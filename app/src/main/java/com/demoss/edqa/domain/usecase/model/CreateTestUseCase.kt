package com.demoss.edqa.domain.usecase.model

import com.demoss.edqa.domain.gateway.TestModelRepository
import com.demoss.edqa.domain.model.TestModel
import com.demoss.edqa.domain.usecase.base.RxUseCaseSingle
import io.reactivex.Single

class CreateTestUseCase(val repository: TestModelRepository) :
    RxUseCaseSingle<Int, CreateTestUseCase.Params>() {

    override fun buildUseCaseObservable(params: Params): Single<Int> =
        repository.createTest(params.test)

    data class Params(
        val test: TestModel
    )
}