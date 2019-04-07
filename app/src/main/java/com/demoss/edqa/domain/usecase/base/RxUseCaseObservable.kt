package com.demoss.edqa.domain.usecase.base

import com.demoss.edqa.util.setDefaultSchedulers
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver

abstract class RxUseCaseObservable<T, Params> : BaseRxUseCase<Params, Observable<T>, DisposableObserver<T>>() {
    final override fun execute(observer: DisposableObserver<T>, params: Params) {
        addDisposable(
            buildUseCaseObservable(params)
                .setDefaultSchedulers()
                .subscribeWith(observer)
        )
    }
}