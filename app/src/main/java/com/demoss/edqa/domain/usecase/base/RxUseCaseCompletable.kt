package com.demoss.edqa.domain.usecase.base

import com.demoss.edqa.util.setDefaultSchedulers
import io.reactivex.Completable
import io.reactivex.observers.DisposableCompletableObserver

abstract class RxUseCaseCompletable<Params> : BaseRxUseCase<Params, Completable, DisposableCompletableObserver>() {
    override fun execute(observer: DisposableCompletableObserver, params: Params) {
        addDisposable(
            buildUseCaseObservable(params)
                .setDefaultSchedulers()
                .subscribeWith(observer)
        )
    }
}