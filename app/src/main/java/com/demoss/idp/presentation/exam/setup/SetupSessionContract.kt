package com.demoss.idp.presentation.exam.setup

import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView
import com.demoss.idp.domain.model.TestModel

interface SetupSessionContract {

    interface Presenter : BasePresenter {
        fun getTest(): TestModel
        fun setupSession(isShuffled: Boolean, timeInMinutes: Long, numberOfQuestions: Int)
    }
    interface View : BaseView
}