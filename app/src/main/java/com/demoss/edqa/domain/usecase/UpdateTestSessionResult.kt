package com.demoss.edqa.domain.usecase

import com.demoss.edqa.domain.gateway.TestModelRepository
import com.demoss.edqa.domain.model.TestModel
import com.demoss.edqa.domain.usecase.base.RxUseCaseCompletable
import io.reactivex.Completable

class UpdateTestSessionResult(private val testRepository: TestModelRepository) : RxUseCaseCompletable<UpdateTestSessionResult.Params>() {

    override fun buildUseCaseObservable(params: Params): Completable = testRepository.updateTest(params.test)

    class Params(val test: TestModel)
}