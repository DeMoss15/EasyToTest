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
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toObservable

class SaveChangesUseCase(
    private val testRepository: TestModelRepository,
    private val questionRepository: QuestionModelRepository,
    private val answerRepository: AnswerModelRepository
) {

    fun save(test: TestModel): Completable = when (test.status) {
            EntityStatus.NEW -> testRepository.createTest(test).flatMapCompletable {
                filterAndSaveQuestions(it, test.questions)
            }
            EntityStatus.SAVED -> filterAndSaveQuestions(test.id, test.questions)
            EntityStatus.MODIFIED -> testRepository.updateTest(test).andThen(
                filterAndSaveQuestions(test.id, test.questions)
            )
            EntityStatus.DROPPED -> testRepository.removeTest(test).andThen(
                filterAndSaveQuestions(test.id, test.questions.map { it.apply { status = EntityStatus.DROPPED } })
            )
        }.setDefaultSchedulers()

    private fun filterAndSaveQuestions(testId: Int, questions: List<QuestionModel>): Completable =
        if (questions.isEmpty()) Completable.complete() else
            listOf(
                questions.filter { it.status == EntityStatus.NEW },
                questions.filter { it.status == EntityStatus.SAVED },
                questions.filter { it.status == EntityStatus.MODIFIED },
                questions.filter { it.status == EntityStatus.DROPPED }
            ).toObservable().flatMapCompletable {
                saveFilteredQuestions(testId, it)
            }

    private fun saveFilteredQuestions(testId: Int, questions: List<QuestionModel>): Completable =
        if (questions.isEmpty()) Completable.complete() else
            when (questions.first().status) {
                EntityStatus.NEW -> questionRepository.createQuestion(testId, *questions.toTypedArray())
                    .flatMapObservable { it.toObservable() }
                    .zipWith(
                        questions.toObservable(),
                        BiFunction { id: Int, question: QuestionModel -> id to question })
                    .flatMapCompletable { filterAndSaveAnswers(it.first, it.second.answers) }
                EntityStatus.SAVED -> questions.toObservable().flatMapCompletable { filterAndSaveAnswers(it.id, it.answers) }
                EntityStatus.MODIFIED -> questionRepository.updateQuestion(testId, *questions.toTypedArray()).andThen(
                    questions.toObservable().flatMapCompletable { filterAndSaveAnswers(it.id, it.answers) }
                )
                EntityStatus.DROPPED -> questionRepository.removeQuestion(testId, *questions.toTypedArray()).andThen(
                    questions.toObservable().flatMapCompletable { question ->
                        filterAndSaveAnswers(question.id, question.answers.map { it.apply { status = EntityStatus.DROPPED } })
                    }
                )
            }

    private fun filterAndSaveAnswers(questionId: Int, answers: List<AnswerModel>): Completable =
        if (answers.isEmpty()) Completable.complete() else
            listOf(
                answers.filter { it.status == EntityStatus.NEW },
                answers.filter { it.status == EntityStatus.SAVED },
                answers.filter { it.status == EntityStatus.MODIFIED },
                answers.filter { it.status == EntityStatus.DROPPED }
            ).toObservable().flatMapCompletable {
                saveFilteredAnswers(questionId, it)
            }

    private fun saveFilteredAnswers(questionId: Int, answers: List<AnswerModel>): Completable =
        if (answers.isEmpty()) Completable.complete() else
            when (answers.first().status) {
                EntityStatus.NEW -> answerRepository.createAnswer(questionId, *answers.toTypedArray())
                EntityStatus.SAVED -> Completable.complete()
                EntityStatus.MODIFIED -> answerRepository.updateAnswer(questionId, *answers.toTypedArray())
                EntityStatus.DROPPED -> answerRepository.removeAnswer(questionId, *answers.toTypedArray())
            }
}