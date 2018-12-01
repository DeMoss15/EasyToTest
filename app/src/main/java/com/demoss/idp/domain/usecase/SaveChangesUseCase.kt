package com.demoss.idp.domain.usecase

import com.demoss.idp.data.repository.AnswerModelRepository
import com.demoss.idp.data.repository.QuestionModelRepository
import com.demoss.idp.data.repository.TestModelRepository
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.EntityStatus
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.util.setDefaultSchedulers
import io.reactivex.Completable

class SaveChangesUseCase(
    private val testRepository: TestModelRepository,
    private val questionRepository: QuestionModelRepository,
    private val answerRepository: AnswerModelRepository
) {

    fun save(test: TestModel): Completable = when (test.status) {
        EntityStatus.NEW -> testRepository.createTest(test).andThen(
            saveQuestions(test.id, test.questions)
        )
        EntityStatus.SAVED -> Completable.complete()
        EntityStatus.MODIFIED -> {
            testRepository.updateTest(test).apply {
                fun saveWithStatus(status: EntityStatus) {
                    andThen(saveQuestions(test.id, test.questions.filter { question ->
                        question.status == status
                    }))
                }
                saveWithStatus(EntityStatus.NEW)
                saveWithStatus(EntityStatus.MODIFIED)
                saveWithStatus(EntityStatus.DROPPED)
            }
        }
        EntityStatus.DROPPED -> testRepository.removeTest(test)
    }.setDefaultSchedulers()

    private fun saveQuestions(testId: Int, questions: List<QuestionModel>): Completable =
        if (questions.isEmpty()) Completable.complete() else
            when (questions.first().status) {
                EntityStatus.NEW -> questionRepository.createQuestion(testId, *questions.toTypedArray()).andThen {
                    questions.forEach { question -> saveAnswers(question.id, question.answers) }
                }
                EntityStatus.SAVED -> Completable.complete()
                EntityStatus.MODIFIED -> {
                    questionRepository.updateQuestion(testId, *questions.toTypedArray()).apply {
                        questions.forEach { question ->
                            fun saveWithStatus(status: EntityStatus) {
                                andThen(saveAnswers(question.id, question.answers.filter { answer ->
                                    answer.status == status
                                }))
                            }
                            saveWithStatus(EntityStatus.NEW)
                            saveWithStatus(EntityStatus.MODIFIED)
                            saveWithStatus(EntityStatus.DROPPED)
                        }
                    }
                }
                EntityStatus.DROPPED -> questionRepository.removeQuestion(testId, *questions.toTypedArray())
            }

    private fun saveAnswers(questionId: Int, answers: List<AnswerModel>): Completable =
        if (answers.isEmpty()) Completable.complete() else
            when (answers.first().status) {
                EntityStatus.NEW -> answerRepository.createAnswer(questionId, *answers.toTypedArray())
                EntityStatus.SAVED -> Completable.complete()
                EntityStatus.MODIFIED -> answerRepository.updateAnswer(questionId, *answers.toTypedArray())
                EntityStatus.DROPPED -> answerRepository.removeAnswer(questionId, *answers.toTypedArray())
            }
}