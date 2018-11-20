package com.demoss.idp.presentation.main.tests

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.GetTestsUserCase
import com.demoss.idp.util.pagination.Paginator
import io.reactivex.subjects.PublishSubject

class TestsPresenter(private val getTestsUserCase: GetTestsUserCase) :
    TestsContract.Presenter, BasePresenterImpl<TestsContract.View>() {

    private val pagesPublishSubject = PublishSubject.create<Int>()
    private lateinit var paginator: Paginator<TestModel>

    override fun onCreateView() {
        super.onCreateView()
        paginator = Paginator<TestModel>(
            getTestsUserCase.buildUseCaseObservable(GetTestsUserCase.Params(pagesPublishSubject)),
            view as Paginator.ViewController<TestModel>
        ) {
            pagesPublishSubject.onNext(it)
        }
    }

    override fun onViewShown() {
        super.onViewShown()
        paginator.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        paginator.release()
    }

    override fun loadMore() {
        paginator.loadNewPage()
    }
}