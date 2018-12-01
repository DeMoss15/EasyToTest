package com.demoss.idp.domain.usecase.model

import com.demoss.idp.data.repository.TestModelRepository
import com.demoss.idp.domain.usecase.base.RxUseCaseSingle
import io.reactivex.Single

class GetLastTestIdUseCase(private val testRepository: TestModelRepository): RxUseCaseSingle<Int, GetLastTestIdUseCase.Params>() {

    override fun buildUseCaseObservable(params: Params): Single<Int> {
        return testRepository.getNextId()
    }

    class Params()
}