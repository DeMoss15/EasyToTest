package com.demoss.idp.presentation.exam.exam

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.usecase.TestSessionUseCase
import io.reactivex.disposables.CompositeDisposable

class ExamPresenter(private val testSessionUseCase: TestSessionUseCase) : BasePresenterImpl<ExamContract.View>(),
    ExamContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onViewHidden() {
        super.onViewHidden()
        compositeDisposable.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }

    override fun setExtra(testId: Int, isResults: Boolean) {
        compositeDisposable.addAll(testSessionUseCase.setTestId(testId).subscribe(
            {
                if (isResults) view?.showResults()
                else view?.showSettings()
            },
            {
                it.printStackTrace()
                view?.showToast(it.localizedMessage)
            }
        ))
    }
}