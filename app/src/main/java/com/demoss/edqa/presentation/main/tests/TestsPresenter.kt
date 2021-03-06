package com.demoss.edqa.presentation.main.tests

import com.demoss.edqa.base.mvp.BasePresenterImpl
import com.demoss.edqa.domain.model.TestModel
import com.demoss.edqa.domain.usecase.ParseFileUseCase
import com.demoss.edqa.domain.usecase.ShareTestUseCase
import com.demoss.edqa.domain.usecase.model.GetTestsUseCase
import com.demoss.edqa.util.pagination.Paginator
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.io.InputStream

class TestsPresenter(
    private val getTestsUseCase: GetTestsUseCase,
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
                getTestsUseCase.buildUseCaseObservable(GetTestsUseCase.Params(pagesPublishSubject)),
                view as Paginator.ViewController<TestModel>
        ) {
            pagesPublishSubject.onNext(it)
        }
    }

    override fun onViewShown() {
        paginator.refresh()
        super.onViewShown()
    }

    override fun onViewHidden() {
        super.onViewHidden()
        parseFileUseCase.clear()
        shareTestUseCase.clear()
        compositeDisposable.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        paginator.release()
        parseFileUseCase.dispose()
        shareTestUseCase.dispose()
        compositeDisposable.dispose()
    }

    override fun loadMore() {
        paginator.loadNewPage()
    }

    override fun share(test: TestModel) {
        shareTestUseCase.execute(object : DisposableSingleObserver<String>() {
            override fun onSuccess(t: String) {
                view?.share(t, test.name)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                view?.showToast(e.localizedMessage)
            }
        }, ShareTestUseCase.Params(test))
    }

    override fun parseFileStream(stream: Single<InputStream?>) {
        view?.showParsingProgress(true)
        compositeDisposable.add(stream.subscribeOn(Schedulers.computation()).subscribe(
                { if (it != null) parse(it) },
                { it.printStackTrace() }
        ))
    }

    private fun parse(stream: InputStream) {
        parseFileUseCase.execute(object : DisposableCompletableObserver() {

            override fun onComplete() {
                view?.showParsingProgress(false)
                paginator.restart()
            }

            override fun onError(e: Throwable) {
                view?.showParsingProgress(false)
                view?.showToast(e.localizedMessage)
                e.printStackTrace()
            }
        }, ParseFileUseCase.Params(stream))
    }
}