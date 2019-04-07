package com.demoss.edqa.presentation.main.tests

import com.demoss.edqa.base.mvp.BasePresenter
import com.demoss.edqa.base.mvp.BaseView
import com.demoss.edqa.domain.model.TestModel
import com.demoss.edqa.presentation.main.main.MainFragment
import com.demoss.edqa.util.pagination.Paginator
import io.reactivex.Single
import java.io.InputStream

interface TestsContract {

    interface Presenter : BasePresenter {
        fun loadMore()
        fun share(test: TestModel)
        fun parseFileStream(stream: Single<InputStream?>)
    }

    interface View : BaseView, Paginator.ViewController<TestModel>, MainFragment {
        fun share(string: String, testName: String)
        fun showParsingProgress(isVisible: Boolean)
    }
}