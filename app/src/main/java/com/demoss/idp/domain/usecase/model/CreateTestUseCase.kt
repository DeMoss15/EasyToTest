package com.demoss.idp.domain.usecase.model

import com.demoss.idp.domain.gateway.TestModelRepository
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.base.RxUseCaseSingle
import io.reactivex.Single

class CreateTestUseCase(val repository: TestModelRepository) :
    RxUseCaseSingle<Int, CreateTestUseCase.Params>() {

    override fun buildUseCaseObservable(params: Params): Single<Int> =
        repository.createTest(params.test)

    data class Params(
        val test: TestModel
    )
}