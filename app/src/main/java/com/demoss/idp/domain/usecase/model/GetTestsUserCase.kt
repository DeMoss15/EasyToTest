package com.demoss.idp.domain.usecase.model

import com.demoss.idp.data.repository.TestModelRepository
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.base.RxUseCaseObservable
import io.reactivex.Observable

class GetTestsUserCase(val repository: TestModelRepository) : RxUseCaseObservable<List<TestModel>, GetTestsUserCase.Params>() {

    override fun buildUseCaseObservable(params: Params): Observable<List<TestModel>> {
        return repository.getTests(params.pagesObservable)
    }

    class Params(
        val pagesObservable: Observable<Int>
    )
}