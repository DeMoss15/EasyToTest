package com.demoss.idp.domain.usecase.model

import com.demoss.idp.data.repository.TestModelRepository
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.base.RxUseCaseCompletable
import io.reactivex.Completable

class DeleteTestUseCase(val repository: TestModelRepository) :
    RxUseCaseCompletable<TestModel, DeleteTestUseCase.Params>() {

    override fun buildUseCaseObservable(params: Params): Completable =
        repository.removeTest(params.test)

    data class Params(
        val test: TestModel
    )
}