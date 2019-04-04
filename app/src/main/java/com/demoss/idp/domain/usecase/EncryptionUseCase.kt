package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.usecase.base.RxUseCaseSingle
import com.demoss.idp.util.Constants
import com.demoss.idp.util.EmptyConstants.EMPTY_STRING
import com.demoss.idp.util.encryption.decrypt
import com.demoss.idp.util.encryption.encrypt
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