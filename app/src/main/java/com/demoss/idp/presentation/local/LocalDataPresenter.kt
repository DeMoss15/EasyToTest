package com.demoss.idp.presentation.local

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.GetTestsUserCase
import com.demoss.idp.util.pagination.Paginator
import io.reactivex.subjects.PublishSubject

class LocalDataPresenter(private val getTestsUserCase: GetTestsUserCase) :
    LocalDataContract.Presenter, BasePresenterImpl<LocalDataContract.View>() {

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

    override fun loadMore() {
        paginator.loadNewPage()
    }
}