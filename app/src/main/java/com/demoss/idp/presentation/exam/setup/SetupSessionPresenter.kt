package com.demoss.idp.presentation.exam.setup

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.TestSessionUseCase

class SetupSessionPresenter(private val testSessionUseCase: TestSessionUseCase) : BasePresenterImpl<SetupSessionContract.View>(), SetupSessionContract.Presenter {

    override fun getTest(): TestModel = testSessionUseCase.getTest()

    override fun setupSession(isShuffled: Boolean, timeInMinutes: Long, numberOfQuestions: Int) =
            testSessionUseCase.setupSession(isShuffled, timeInMinutes, numberOfQuestions)
}