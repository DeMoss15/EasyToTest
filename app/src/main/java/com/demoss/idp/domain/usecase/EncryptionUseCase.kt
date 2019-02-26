package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.usecase.base.RxUseCaseSingle
import com.demoss.idp.util.Constants
import com.demoss.idp.util.EmptyConstants.EMPTY_STRING
import com.demoss.idp.util.encription.CryptLib
import com.demoss.idp.util.generateKey
import io.reactivex.Single

class EncryptionUseCase : RxUseCaseSingle<String, EncryptionUseCase.Params>() {

    override fun buildUseCaseObservable(params: Params): Single<String> =
        if (params.input.startsWith(Constants.JSON_PREFIX)) {
            decrypt(params.input)
        } else {
            encrypt(params.input)
        }

    private fun encrypt(input: String): Single<String> {
        val key = generateKey(Constants.KEY_LENGTH)
        val encryptedInput = CryptLib().encryptPlainTextWithRandomIV(input, key)

        return Single.just(
            Constants.JSON_PREFIX +
                    encryptedInput +
                    key
        )
    }

    private fun decrypt(input: String): Single<String> {
        val key = getKeyFromEncryptedInput(input)
        val encryptedTest = cleanInput(input)
        return Single.just(
            CryptLib().decryptCipherTextWithRandomIV(encryptedTest, key)
        )
    }

    private fun cleanInput(input: String): String = input
        .replace(Constants.JSON_PREFIX, EMPTY_STRING)
        .replace(getKeyFromEncryptedInput(input), EMPTY_STRING)

    private fun getKeyFromEncryptedInput(input: String): String =
        input.substring(input.length - Constants.KEY_LENGTH)

    data class Params(
        val input: String
    )
}