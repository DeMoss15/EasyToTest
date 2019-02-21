package com.demoss.idp.presentation.exam.exam

import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView

interface ExamContract {

    interface Presenter : BasePresenter {
        fun setExtra(testId: Int, isResults: Boolean)
    }
    interface View : BaseView {
        fun showSettings()
        fun showResults()
    }
}