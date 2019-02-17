package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.EntityStatus
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.model.GetTestUseCase
import io.reactivex.Single
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver

class EditTestUseCase(
    private val getTestUseCase: GetTestUseCase,
    private val saveChangesUseCase: SaveChangesUseCase
) {
    private lateinit var currentTest: TestModel
    private lateinit var currentQuestion: QuestionModel
    private lateinit var currentAnswer: AnswerModel

    private fun saveChanges(completableObserver: DisposableCompletableObserver) {
        TempEntitiesFabric.cleanTempIds(currentTest)
        saveChangesUseCase.save(currentTest).subscribe(completableObserver)
    }

    // Test ========================================================================================
    fun getTest(singleObserver: DisposableSingleObserver<TestModel>, testId: Int) {
        if (::currentTest.isInitialized && currentTest.id == testId) {
            Single.just(currentTest)
        } else {
            getTestUseCase.buildUseCaseObservable(GetTestUseCase.Params(testId))
                .onErrorReturn { TempEntitiesFabric.createTempTest() }
                .map { it.apply { currentTest = it } }
        }.subscribe(singleObserver)
    }

    fun saveTest(testName: String, examMode: Boolean, password: String, timer: Long, questionsAmount: Int, completableObserver: DisposableCompletableObserver) {
        currentTest.setModified()
        currentTest.name = testName
        currentTest.timer = timer
        currentTest.password = password
        currentTest.examMode = examMode
        currentTest.questionsAmount = questionsAmount
        saveChanges(completableObserver)
    }

    fun deleteTest(completableObserver: DisposableCompletableObserver) {
        currentTest.status = EntityStatus.DROPPED
        saveChanges(completableObserver)
    }

    fun cancelTestEditing() {
        currentTest = TempEntitiesFabric.createTempTest()
    }

    // Question ====================================================================================
    fun getQuestion(singleObserver: DisposableSingleObserver<QuestionModel>, questionId: Int) =
        if (::currentQuestion.isInitialized && currentQuestion.id == questionId) {
            Single.just(currentQuestion)
        } else {
            Single.just(currentTest.questions.find { it.id == questionId }
                ?: TempEntitiesFabric.createTempQuestion())
                .map { it.apply { currentQuestion = it } }
        }.subscribe(singleObserver)

    fun saveQuestion(question: String) {
        if (currentQuestion.isTemp() && !currentTest.questions.contains(currentQuestion))
            currentTest.questions.add(currentQuestion)
        currentQuestion.apply {
            setModified()
            text = question
        }
    }

    fun deleteQuestion() {
        currentQuestion.status = EntityStatus.DROPPED
    }

    fun deleteQuestion(question: QuestionModel) {
        question.status = EntityStatus.DROPPED
    }

    fun cancelQuestionEditing() {
        currentQuestion.answers.map { it.status = EntityStatus.SAVED }
    }

    // Answer ========================================================================================
    fun getAnswer(singleObserver: DisposableSingleObserver<AnswerModel>, answerId: Int) {
        if (::currentAnswer.isInitialized && currentAnswer.id == answerId) {
            Single.just(currentAnswer)
        } else {
            Single.just(currentQuestion.answers.find { it.id == answerId }
                ?: TempEntitiesFabric.createTempAnswer())
                .map { it.apply { currentAnswer = it } }
        }.subscribe(singleObserver)
    }

    fun saveAnswer(answer: String, isRight: Boolean) {
        if (currentAnswer.isTemp() && !currentQuestion.answers.contains(currentAnswer))
            currentQuestion.answers.add(currentAnswer)
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