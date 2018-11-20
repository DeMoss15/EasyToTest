package com.demoss.idp.presentation.main.edit.test

import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.util.pagination.Paginator

interface EditTestContract {

    interface Presenter : BasePresenter {
        fun init(testId: Int)
    }

    interface View : BaseView {
        fun showTest(test: TestModel)
    }
}