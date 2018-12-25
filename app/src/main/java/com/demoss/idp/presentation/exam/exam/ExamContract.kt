package com.demoss.idp.presentation.exam.exam

import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView

interface ExamContract {

    interface Presenter: BasePresenter {
        fun setTestId(testId: Int)
    }
    interface View: BaseView {
        fun showSettings()
    }
}