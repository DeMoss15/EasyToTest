package com.demoss.idp.presentation.main.tests

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.ParseFileUseCase
import com.demoss.idp.domain.usecase.ShareTestUseCase
import com.demoss.idp.domain.usecase.model.GetTestsUserCase
import com.demoss.idp.util.pagination.Paginator
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.io.InputStream

class TestsPresenter(
    private val getTestsUserCase: GetTestsUserCase,
    private val parseFileUseCase: ParseFileUseCase,
    private val shareTestUseCase: ShareTestUseCase
) :
    TestsContract.Presenter, BasePresenterImpl<TestsContract.View>() {

    private val pagesPublishSubject = PublishSubject.create<Int>()
    private lateinit var paginator: Paginator<TestModel>
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView() {
        super.onCreateView()
        paginator = Paginator(
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

    override fun onViewHidden() {
        super.onViewHidden()
        getTestsUserCase.clear()
        compositeDisposable.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        paginator.release()
        parseFileUseCase.dispose()
        getTestsUserCase.dispose()
        compositeDisposable.dispose()
    }

    override fun loadMore() {
        paginator.loadNewPage()
    }

    override fun share(test: TestModel) {
        shareTestUseCase.execute(object: DisposableSingleObserver<String>(){
            override fun onSuccess(t: String) {
                view?.share(t)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                view?.showToast(e.localizedMessage)
            }
        }, ShareTestUseCase.Params(test))
    }

    override fun parseFileStream(stream: Single<InputStream?>) {
        compositeDisposable.add(stream.subscribeOn(Schedulers.computation()).subscribe(
                { if (it != null) parse(it) },
                { it.printStackTrace() }
        ))
    }

    private fun parse(stream: InputStream) {
        parseFileUseCase.execute(object : DisposableCompletableObserver() {
            override fun onComplete() {
                paginator.refresh()
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                view?.showToast(e.localizedMessage)
            }
        }, ParseFileUseCase.Params(stream))
    }
}