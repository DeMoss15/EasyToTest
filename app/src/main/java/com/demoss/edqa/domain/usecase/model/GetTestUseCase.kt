package com.demoss.edqa.domain.usecase.model

import com.demoss.edqa.domain.gateway.TestModelRepository
import com.demoss.edqa.domain.model.TestModel
import com.demoss.edqa.domain.usecase.base.RxUseCaseSingle
import io.reactivex.Single

class GetTestUseCase(val repository: TestModelRepository) : RxUseCaseSingle<TestModel, GetTestUseCase.Params>() {

    override fun buildUseCaseObservable(params: Params): Single<TestModel> =
        repository.getTest(params.testId)

    data class Params(
        val testId: Int
    )
}