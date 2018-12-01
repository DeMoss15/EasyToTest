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
        EntityStatus.NEW -> testRepository.createTest(test).andThen {
            saveQuestions(test.id, test.questions, EntityStatus.NEW)
        }
        EntityStatus.SAVED -> Completable.complete()
        EntityStatus.MODIFIED -> {
            testRepository.updateTest(test).andThen {
                // save new questions
                saveQuestions(
                    test.id,
                    test.questions.filter { question -> question.status == EntityStatus.NEW },
                    EntityStatus.NEW
                ).andThen {
                    // save modified questions
                    saveQuestions(
                        test.id,
                        test.questions.filter { question -> question.status == EntityStatus.MODIFIED },
                        EntityStatus.MODIFIED
                    ).andThen {
                        // remove dropped questions
                        saveQuestions(
                            test.id,
                            test.questions.filter { question -> question.status == EntityStatus.DROPPED },
                            EntityStatus.DROPPED
                        )
                    }
                }
            }
        }
        EntityStatus.DROPPED -> testRepository.removeTest(test)
    }.setDefaultSchedulers()

    private fun saveQuestions(testId: Int, questions: List<QuestionModel>, status: EntityStatus): Completable =
        when (status) {
            EntityStatus.NEW -> questionRepository.createQuestion(testId, *questions.toTypedArray()).andThen {
                questions.forEach { question -> saveAnswers(question.id, question.answers, status) }
            }
            EntityStatus.SAVED -> Completable.complete()
            EntityStatus.MODIFIED -> {
                questionRepository.updateQuestion(testId, *questions.toTypedArray()).andThen {
                    questions.forEach { question ->
                        // save new answers
                        saveAnswers(
                            question.id,
                            question.answers.filter { answer -> answer.status == EntityStatus.NEW },
                            EntityStatus.NEW
                        ).andThen {
                            // save modified answers
                            saveAnswers(
                                question.id,
                                question.answers.filter { answer -> answer.status == EntityStatus.MODIFIED },
                                EntityStatus.MODIFIED
                            ).andThen {
                                // remove dropped answers
                                saveAnswers(
                                    question.id,
                                    question.answers.filter { answer -> answer.status == EntityStatus.DROPPED },
                                    EntityStatus.DROPPED
                                )
                            }
                        }
                    }
                }
            }
            EntityStatus.DROPPED -> questionRepository.removeQuestion(testId, *questions.toTypedArray())
        }

    private fun saveAnswers(questionId: Int, answers: List<AnswerModel>, status: EntityStatus): Completable =
        when (status) {
            EntityStatus.NEW -> answerRepository.createAnswer(questionId, *answers.toTypedArray())
            EntityStatus.SAVED -> Completable.complete()
            EntityStatus.MODIFIED -> answerRepository.updateAnswer(questionId, *answers.toTypedArray())
            EntityStatus.DROPPED -> answerRepository.removeAnswer(questionId, *answers.toTypedArray())
        }
}