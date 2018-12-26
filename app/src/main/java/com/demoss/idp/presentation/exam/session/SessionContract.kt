package com.demoss.idp.presentation.exam.session

import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.QuestionModel

interface SessionContract {

    interface Presenter: BasePresenter {
        fun startSession()
        fun setAnswer(answer: AnswerModel)
    }
    interface View: BaseView {
        fun showTimer(time: String)
        fun showQuestion(question: QuestionModel)
        fun showCounter(counter: String)
        fun navigateToResults()
    }
}