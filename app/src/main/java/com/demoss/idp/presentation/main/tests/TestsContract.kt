package com.demoss.idp.presentation.main.tests

import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.util.pagination.Paginator

interface TestsContract {

    interface Presenter : BasePresenter {
        fun loadMore()
    }

    interface View : BaseView, Paginator.ViewController<TestModel>
}