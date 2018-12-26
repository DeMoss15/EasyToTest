package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.EntityStatus
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.model.GetTestUseCase
import com.demoss.idp.util.Constants.NEW_ENTITY_ID
import com.demoss.idp.util.EmptyConstants
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
            .onErrorReturn { TempElementsFabric.createTempTest() }
            .map {
                it.apply { currentTest = it }
            }
            .subscribe(singleObserver)
    }

    fun saveTest(testName: String, completableObserver: DisposableCompletableObserver) {
        currentTest.setModified()
        currentTest.name = testName
        TempElementsFabric.cleanTempIds(currentTest)
        saveChangesUseCase.save(currentTest).subscribe(completableObserver)
    }

    fun deleteTest(completableObserver: DisposableCompletableObserver) {
        currentTest.status = EntityStatus.DROPPED
        saveChangesUseCase.save(currentTest).subscribe(completableObserver)
    }

    // Question ====================================================================================
    fun getQuestion(singleObserver: DisposableSingleObserver<QuestionModel>, questionId: Int) =
        if (::currentQuestion.isInitialized && questionId == currentQuestion.id) Single.just(currentQuestion).subscribe(singleObserver)
        else Single.just(
            if (questionId == NEW_ENTITY_ID) TempElementsFabric.createTempQuestion()
            else currentTest.questions.find { it.id == questionId } ?: throw RuntimeException("wrong question id")
        )
            .map { it.apply { currentQuestion = it } }
            .subscribe(singleObserver)

    fun saveQuestion(question: String) {
        if (currentQuestion.isTemp() && !currentTest.questions.contains(currentQuestion)) currentTest.questions.add(
            currentQuestion
        )
        currentQuestion.setModified()
        currentQuestion.text = question
    }

    fun deleteQuestion() {
        currentQuestion.status = EntityStatus.DROPPED
    }

    // Answer ========================================================================================
    fun getAnswer(singleObserver: DisposableSingleObserver<AnswerModel>, answerId: Int) {
        if (::currentAnswer.isInitialized && answerId == currentAnswer.id) Single.just(currentAnswer).subscribe(singleObserver)
        else Single.just(
            if (answerId == NEW_ENTITY_ID) TempElementsFabric.createTempAnswer()
            else currentQuestion.answers.find { it.id == answerId } ?: throw RuntimeException("wrong question id")
        )
            .map { it.apply { currentAnswer = it } }
            .subscribe(singleObserver)
    }

    fun saveAnswer(answer: String, isRight: Boolean) {
        if (currentAnswer.isTemp() && !currentQuestion.answers.contains(currentAnswer)) currentQuestion.answers.add(
            currentAnswer
        )
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