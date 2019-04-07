package com.demoss.edqa.domain.usecase.model

import com.demoss.edqa.domain.gateway.TestModelRepository
import com.demoss.edqa.domain.model.TestModel
import com.demoss.edqa.domain.usecase.base.RxUseCaseObservable
import io.reactivex.Observable

class GetTestsUseCase(val repository: TestModelRepository) : RxUseCaseObservable<List<TestModel>, GetTestsUseCase.Params>() {

    override fun buildUseCaseObservable(params: Params): Observable<List<TestModel>> {
        return repository.getTests(params.pagesObservable)
    }

    class Params(
        val pagesObservable: Observable<Int>
    )
}