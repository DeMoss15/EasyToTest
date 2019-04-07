package com.demoss.edqa.presentation.main.edit.answer

import com.demoss.edqa.base.mvp.BasePresenter
import com.demoss.edqa.base.mvp.BaseView
import com.demoss.edqa.domain.model.AnswerModel
import com.demoss.edqa.presentation.main.main.MainFragment

interface EditAnswerContract {

    interface Presenter : BasePresenter {
        var answerId: Int
        fun saveAnswer(answer: String, isRight: Boolean)
        fun deleteAnswer(answer: AnswerModel)
    }

    interface View : BaseView, MainFragment {
        fun showAnswer(answer: AnswerModel)
        fun showEmptyDataError()
        fun navigateBack()
    }
}