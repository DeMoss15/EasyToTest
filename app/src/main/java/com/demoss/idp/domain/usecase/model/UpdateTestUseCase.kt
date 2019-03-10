package com.demoss.idp.domain.usecase.model

import com.demoss.idp.domain.gateway.TestModelRepository
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.base.RxUseCaseCompletable
import io.reactivex.Completable

class UpdateTestUseCase(val repository: TestModelRepository) :
    RxUseCaseCompletable<UpdateTestUseCase.Params>() {

    override fun buildUseCaseObservable(params: Params): Completable =
        repository.updateTest(params.test)

    data class Params(
        val test: TestModel
    )
}