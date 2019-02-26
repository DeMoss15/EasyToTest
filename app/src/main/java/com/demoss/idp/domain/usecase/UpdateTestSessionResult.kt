package com.demoss.idp.domain.usecase

import com.demoss.idp.data.repository.TestModelRepository
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.base.RxUseCaseCompletable
import io.reactivex.Completable

class UpdateTestSessionResult(private val testRepository: TestModelRepository) : RxUseCaseCompletable<UpdateTestSessionResult.Params>() {

    override fun buildUseCaseObservable(params: Params): Completable = testRepository.updateTest(params.test)

    class Params(val test: TestModel)
}