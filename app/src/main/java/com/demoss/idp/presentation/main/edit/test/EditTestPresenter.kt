package com.demoss.idp.presentation.main.edit.test

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.EditTestUseCase
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver

class EditTestPresenter(private val editTestUseCase: EditTestUseCase)
    : BasePresenterImpl<EditTestContract.View>(), EditTestContract.Presenter {

    private lateinit var test: TestModel

    override fun init(testId: Int) {
        editTestUseCase.getTest(object : DisposableSingleObserver<TestModel>() {
            override fun onSuccess(t: TestModel) {
                view?.showTest(t)
                test = t
            }

            override fun onError(e: Throwable) {
                view?.showToast(e.localizedMessage)
            }
        }, testId)
    }

    override fun saveTest() {
        editTestUseCase.saveTest(getCompletableObserver())
    }

    override fun deleteTest() {
        editTestUseCase.deleteTest(getCompletableObserver())
    }

    private fun getCompletableObserver(): DisposableCompletableObserver =
            object : DisposableCompletableObserver() {
                override fun onComplete() {
                    view?.navigateBack()
                }

                override fun onError(e: Throwable) {
                    view?.showToast(e.localizedMessage)
                }
            }
}