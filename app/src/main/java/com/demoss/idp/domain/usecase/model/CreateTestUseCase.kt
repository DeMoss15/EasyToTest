package com.demoss.idp.domain.usecase.model

import com.demoss.idp.data.repository.TestModelRepository
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.base.RxUseCaseCompletable
import io.reactivex.Completable

class CreateTestUseCase(val repository: TestModelRepository) :
    RxUseCaseCompletable<TestModel, CreateTestUseCase.Params>() {

    // TODO: 09.11.18 solve problem with test id getting (maybe builder + get total tests count use case)
    override fun buildUseCaseObservable(params: Params): Completable =
        repository.createTest(params.test)

    data class Params(
        val test: TestModel
    )
}