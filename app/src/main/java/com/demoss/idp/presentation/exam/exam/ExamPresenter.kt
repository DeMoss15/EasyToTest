package com.demoss.idp.presentation.exam.exam

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.usecase.TestSessionUseCase
import io.reactivex.disposables.CompositeDisposable

class ExamPresenter(private val testSessionUseCase: TestSessionUseCase) : BasePresenterImpl<ExamContract.View>(), ExamContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }

    override fun setTestId(testId: Int) {
        compositeDisposable.addAll(testSessionUseCase.setTestId(testId).subscribe(
                { view?.showSettings() },
                {
                    it.printStackTrace()
                    view?.showToast(it.localizedMessage)
                }
        ))
    }
}