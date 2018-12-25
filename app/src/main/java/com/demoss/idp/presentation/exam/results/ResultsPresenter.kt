package com.demoss.idp.presentation.exam.results

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.usecase.TestSessionUseCase

class ResultsPresenter(private val testSessionUseCase: TestSessionUseCase) : BasePresenterImpl<ResultsContract.View>(),
    ResultsContract.Presenter {

    override fun onViewShown() {
        super.onViewShown()
        view?.showTestName(testSessionUseCase.getTest().name)
        view?.showAnswersStats("${testSessionUseCase.getNumberOfRightAnswers()}/" +
                "${testSessionUseCase.getNumberOfAnswers()}")
        view?.showSpentTime(testSessionUseCase.getTimeSpent())
    }
}