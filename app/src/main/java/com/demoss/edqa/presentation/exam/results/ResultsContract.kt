package com.demoss.edqa.presentation.exam.results

import com.demoss.edqa.base.mvp.BasePresenter
import com.demoss.edqa.base.mvp.BaseView

interface ResultsContract {

    interface Presenter : BasePresenter
    interface View : BaseView {
        fun showTestName(testName: String)
        fun showSpentTime(time: String)
        fun showAnswersStats(stats: String)
    }
}