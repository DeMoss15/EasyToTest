package com.demoss.idp.presentation.main.edit.question

import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.QuestionModel

interface EditQuestionContract {

    interface Presenter : BasePresenter {
        fun getQuestion(questionId: Int)
        fun addAnswer(answer: AnswerModel)
        fun deleteAnswer(answer: AnswerModel)
    }

    interface View : BaseView {
        fun showQuestion(question: QuestionModel)
    }
}