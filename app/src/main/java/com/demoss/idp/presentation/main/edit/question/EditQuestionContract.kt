package com.demoss.idp.presentation.main.edit.question

import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.presentation.main.main.MainFragment

interface EditQuestionContract {

    interface Presenter : BasePresenter {
        fun getQuestion(questionId: Int)
        fun saveQuestion(question: String)
        fun deleteQuestion()
    }

    interface View : BaseView, MainFragment {
        fun showQuestion(question: QuestionModel)
    }
}