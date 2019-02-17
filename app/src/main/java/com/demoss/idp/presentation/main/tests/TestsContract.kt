package com.demoss.idp.presentation.main.tests

import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.presentation.main.main.MainFragment
import com.demoss.idp.util.pagination.Paginator
import io.reactivex.Single
import java.io.InputStream

interface TestsContract {

    interface Presenter : BasePresenter {
        fun loadMore()
        fun share(test: TestModel)
        fun parseFileStream(stream: Single<InputStream?>)
    }

    interface View : BaseView, Paginator.ViewController<TestModel>, MainFragment {
        fun share(string: String)
        fun showParsingProgress(isVisible: Boolean)
    }
}