package com.demoss.edqa.presentation.exam.session

import com.demoss.edqa.base.mvp.BasePresenter
import com.demoss.edqa.base.mvp.BaseView
import com.demoss.edqa.domain.model.AnswerModel
import com.demoss.edqa.domain.model.QuestionModel

interface SessionContract {

    interface Presenter : BasePresenter {
        fun startSession()
        fun setAnswer(answer: AnswerModel)
        fun stopSession()
    }
    interface View : BaseView {
        fun showTimer(time: String)
        fun showQuestion(question: QuestionModel)
        fun showCounter(counter: String)
        fun navigateToResults()
    }
}