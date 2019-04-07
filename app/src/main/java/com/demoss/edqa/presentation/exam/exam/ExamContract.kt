package com.demoss.edqa.presentation.exam.exam

import com.demoss.edqa.base.mvp.BasePresenter
import com.demoss.edqa.base.mvp.BaseView

interface ExamContract {

    interface Presenter : BasePresenter {
        fun setExtra(testId: Int, isResults: Boolean)
    }
    interface View : BaseView {
        fun showSettings()
        fun showResults()
    }
}