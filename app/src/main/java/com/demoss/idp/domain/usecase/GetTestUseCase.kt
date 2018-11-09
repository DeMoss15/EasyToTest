package com.demoss.idp.domain.usecase

import com.demoss.idp.data.repository.TestModelRepository
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.base.RxUseCaseSingle
import io.reactivex.Single

class GetTestUseCase(val repository: TestModelRepository) : RxUseCaseSingle<TestModel, GetTestUseCase.Params>() {

    override fun buildUseCaseObservable(params: Params): Single<TestModel> =
        repository.getTest(params.testId)

    data class Params(
        val testId: Int
    )
}