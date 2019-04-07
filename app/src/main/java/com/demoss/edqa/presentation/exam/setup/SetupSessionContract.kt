package com.demoss.edqa.presentation.exam.setup

import com.demoss.edqa.base.mvp.BasePresenter
import com.demoss.edqa.base.mvp.BaseView
import com.demoss.edqa.domain.model.TestModel

interface SetupSessionContract {

    interface Presenter : BasePresenter {
        fun getTest(): TestModel
        fun setupSession(isShuffled: Boolean, timeInMinutes: String, numberOfQuestions: Int)
    }
    interface View : BaseView {
        fun showTimerValidationError()
        fun showQuestionsAmountValidationError()
        fun navigateToSession()
    }
}