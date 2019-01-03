package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.base.RxUseCaseSingle
import com.demoss.idp.domain.usecase.model.GetTestUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ShareTestUseCase(
    private val getTestUseCase: GetTestUseCase,
    private val jsonConverterUseCase: JsonConverterUseCase,
    private val encryptionUseCase: EncryptionUseCase
) : RxUseCaseSingle<String, ShareTestUseCase.Params>() {

    override fun buildUseCaseObservable(params: Params): Single<String> = getTestUseCase
        .buildUseCaseObservable(GetTestUseCase.Params(params.test.id))
        .observeOn(Schedulers.computation())
        .map { jsonConverterUseCase.createJson(it) }
        .flatMap { encryptionUseCase.buildUseCaseObservable(EncryptionUseCase.Params(it)) }
        .observeOn(AndroidSchedulers.mainThread())

    data class Params(val test: TestModel)
}