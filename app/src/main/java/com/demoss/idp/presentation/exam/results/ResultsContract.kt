package com.demoss.idp.presentation.exam.results

import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView

interface ResultsContract {

    interface Presenter : BasePresenter
    interface View : BaseView {
        fun showTestName(testName: String)
        fun showSpentTime(time: String)
        fun showAnswersStats(stats: String)
    }
}