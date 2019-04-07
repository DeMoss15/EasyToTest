package com.demoss.edqa.presentation.exam.setup

import com.demoss.edqa.base.mvp.BasePresenterImpl
import com.demoss.edqa.domain.model.TestModel
import com.demoss.edqa.domain.usecase.TestSessionUseCase

class SetupSessionPresenter(private val testSessionUseCase: TestSessionUseCase) :
    BasePresenterImpl<SetupSessionContract.View>(), SetupSessionContract.Presenter {

    override fun getTest(): TestModel = testSessionUseCase.getTest()

    override fun setupSession(isShuffled: Boolean, timeInMinutes: String, numberOfQuestions: Int) {
        when {
            !isTimerValid(timeInMinutes) -> view?.showTimerValidationError()
            !isQuestionsAmountValid(numberOfQuestions) -> view?.showQuestionsAmountValidationError()
            else -> {
                testSessionUseCase.setupSession(isShuffled, timeInMinutes.toLong(), numberOfQuestions)
                view?.navigateToSession()
            }
        }
    }

    private fun isTimerValid(timer: String): Boolean = timer.isNotEmpty() && timer.length <= 3

    private fun isQuestionsAmountValid(questionsAmount: Int): Boolean = questionsAmount > 0
}
