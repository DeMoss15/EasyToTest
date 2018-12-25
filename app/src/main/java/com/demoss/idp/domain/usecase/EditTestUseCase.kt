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
    private lateinit var currentAnswer: AnswerModel

    // Test ========================================================================================
    fun getTest(singleObserver: DisposableSingleObserver<TestModel>, testId: Int) {
        if (::currentTest.isInitialized && testId == currentTest.id) Single.just(currentTest).subscribe(singleObserver)
        else getTestUseCase.buildUseCaseObservable(GetTestUseCase.Params(testId))
            .map {
                it.apply { currentTest = it }
            }.subscribe(singleObserver)
    }

    fun saveTest(testName: String, completableObserver: DisposableCompletableObserver) {
        currentTest.setModified()
        currentTest.name = testName
        saveChangesUseCase.save(currentTest).subscribe(completableObserver)
    }

    fun deleteTest(completableObserver: DisposableCompletableObserver) {
        currentTest.status = EntityStatus.DROPPED
        saveChangesUseCase.save(currentTest).subscribe(completableObserver)
    }

    // Question ====================================================================================
    fun getQuestion(singleObserver: DisposableSingleObserver<QuestionModel>, questionId: Int) =
        Single.just(currentTest.questions.find { it.id == questionId }
            ?: throw RuntimeException("wrong question id")).map { it.apply { currentQuestion = it } }
            .subscribe(singleObserver)

    fun saveQuestion(question: String) {
        currentQuestion.setModified()
        currentQuestion.text = question
    }

    fun deleteQuestion() {
        currentQuestion.status = EntityStatus.DROPPED
    }

    // Answer ========================================================================================
    fun getAnswer(singleObserver: DisposableSingleObserver<AnswerModel>, answerId: Int) =
        Single.just(currentQuestion.answers.find { it.id == answerId }
            ?: throw RuntimeException("wrong question id")).map { it.apply { currentAnswer = it } }
            .subscribe(singleObserver)

    fun saveAnswer(answer: String, isRight: Boolean) {
        currentAnswer.apply {
            setModified()
            text = answer
            isRightAnswer = isRight
        }
    }

    fun deleteAnswer(answer: AnswerModel) {
        answer.status = EntityStatus.DROPPED
    }

    // private extension
    private fun AnswerModel.setModified() {
        apply { if (status != EntityStatus.MODIFIED && status != EntityStatus.NEW) status = EntityStatus.MODIFIED }
    }

    private fun QuestionModel.setModified() {
        apply { if (status != EntityStatus.MODIFIED && status != EntityStatus.NEW) status = EntityStatus.MODIFIED }
    }

    private fun TestModel.setModified() {
        apply { if (status != EntityStatus.MODIFIED && status != EntityStatus.NEW) status = EntityStatus.MODIFIED }
    }
}