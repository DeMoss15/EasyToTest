package com.demoss.edqa.domain.usecase

import com.demoss.edqa.domain.usecase.base.RxUseCaseSingle
import com.demoss.edqa.util.Constants
import com.demoss.edqa.util.EmptyConstants.EMPTY_STRING
import com.demoss.edqa.util.encryption.decrypt
import com.demoss.edqa.util.encryption.encrypt
import io.reactivex.Single

class EncryptionUseCase : RxUseCaseSingle<String, EncryptionUseCase.Params>() {

    override fun buildUseCaseObservable(params: Params): Single<String> = Single.just(
        if (params.input.startsWith(Constants.JSON_PREFIX)) {
            params.input.replace(Constants.JSON_PREFIX, EMPTY_STRING).decrypt()
        } else {
            Constants.JSON_PREFIX + params.input.encrypt()
        }
    )

    data class Params(
        val input: String
    )
}