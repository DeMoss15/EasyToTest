package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.EntityStatus
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.model.GetTestUseCase
import io.reactivex.Single
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import java.lang.RuntimeException

class EditTestUseCase(
    private val getTestUseCase: GetTestUseCase,
    private val saveChangesUseCase: SaveChangesUseCase
) {
    private lateinit var currentTest: TestModel
    private lateinit var currentQuestion: QuestionModel

    // Test ========================================================================================
    fun getTest(singleObserver: DisposableSingleObserver<TestModel>, testId: Int) {
        getTestUseCase.buildUseCaseObservable(GetTestUseCase.Params(testId))
            .map {
                it.apply { currentTest = it }
            }.subscribe(singleObserver)
    }

    fun saveTest(testName: String, completableObserver: DisposableCompletableObserver) {
        currentTest.apply {
            if (name != testName) name = testName
            if (name != testName && (status != EntityStatus.MODIFIED || status != EntityStatus.NEW)) status = EntityStatus.MODIFIED
        }
        saveChangesUseCase.save(currentTest).subscribe(completableObserver)
    }

    fun deleteTest(completableObserver: DisposableCompletableObserver) {
        currentTest.status = EntityStatus.DROPPED
        saveChangesUseCase.save(currentTest).subscribe(completableObserver)
    }

    // Question ====================================================================================
    fun getQuestion(questionId: Int): Single<QuestionModel> =
        Single.just(currentTest.questions.find { it.id == questionId }
            ?: throw RuntimeException("wrong question id")).map { it.apply { currentQuestion = it } }

    fun saveQuestion(question: QuestionModel) {
        currentTest.apply {
            if (status != EntityStatus.MODIFIED || status != EntityStatus.NEW) status = EntityStatus.MODIFIED
        }
        when (question.status) {
            EntityStatus.NEW -> {
                currentTest.questions.add(question)
            }
            EntityStatus.SAVED -> {
                question.status = EntityStatus.MODIFIED
            }
            EntityStatus.MODIFIED -> {
            }
            EntityStatus.DROPPED -> {
            }
        }
    }

    fun deleteQuestion(question: QuestionModel) {
        currentTest.apply {
            if (status != EntityStatus.MODIFIED || status != EntityStatus.NEW) status = EntityStatus.MODIFIED
        }
        question.status = EntityStatus.DROPPED
    }

    // Answer ========================================================================================
    fun saveAnswer(answer: AnswerModel) {
        currentQuestion.apply {
            if (status != EntityStatus.MODIFIED || status != EntityStatus.NEW) status = EntityStatus.MODIFIED
        }
        when (answer.status) {
            EntityStatus.NEW -> {
                currentQuestion.answers.add(answer)
            }
            EntityStatus.SAVED -> {
                answer.status = EntityStatus.MODIFIED
            }
            EntityStatus.MODIFIED -> {
            }
            EntityStatus.DROPPED -> {
            }
        }
    }

    fun deleteAnswer(answer: AnswerModel) {
        currentQuestion.apply {
            if (status != EntityStatus.MODIFIED || status != EntityStatus.NEW) status = EntityStatus.MODIFIED
        }
        answer.status = EntityStatus.DROPPED
    }
}