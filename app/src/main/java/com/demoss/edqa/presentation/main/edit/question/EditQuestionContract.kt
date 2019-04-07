package com.demoss.edqa.presentation.main.edit.question

import com.demoss.edqa.base.mvp.BasePresenter
import com.demoss.edqa.base.mvp.BaseView
import com.demoss.edqa.domain.model.AnswerModel
import com.demoss.edqa.domain.model.QuestionModel
import com.demoss.edqa.presentation.main.main.MainFragment

interface EditQuestionContract {

    interface Presenter : BasePresenter {
        var questionId: Int
        fun saveQuestion(question: String)
        fun deleteQuestion()
        fun cancel()
        fun deleteAnswer(answer: AnswerModel)
    }

    interface View : BaseView, MainFragment {
        fun showQuestion(question: QuestionModel)
        fun navigateBack()
        fun showEmptyDataError()
    }
}