package com.demoss.idp.presentation.main.edit.test

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.GetTestUseCase
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver

class EditTestPresenter(
        val getTestUseCase: GetTestUseCase
): BasePresenterImpl<EditTestContract.View>(), EditTestContract.Presenter {

    private var testId = 0

    override fun init(testId: Int) {
        this.testId = testId
        getTestUseCase.execute(object : DisposableSingleObserver<TestModel>() {
            override fun onSuccess(t: TestModel) {
                view?.showTest(t)
            }

            override fun onError(e: Throwable) {
                view?.showToast(e.localizedMessage)
            }
        }, GetTestUseCase.Params(testId))
    }
}