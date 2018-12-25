package com.demoss.idp.presentation.exam.session

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.usecase.TestSessionUseCase

class SessionPresenter(private val testSessionUseCase: TestSessionUseCase) : BasePresenterImpl<SessionContract.View>(), SessionContract.Presenter {

    override fun startSession() {
        testSessionUseCase.runTimer(
                { view?.navigateToResults() }, // on time out or stop timer
                { view?.showTimer(it) } // on tick
        )
        testSessionUseCase.subscribeToQuestions { view?.showQuestion(it) }
    }

    override fun setAnswer(answer: AnswerModel) = testSessionUseCase.setAnswer(answer)
}