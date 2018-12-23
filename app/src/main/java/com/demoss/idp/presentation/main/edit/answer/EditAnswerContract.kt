package com.demoss.idp.presentation.main.edit.answer

import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.presentation.main.main.MainFragment

interface EditAnswerContract {

    interface Presenter : BasePresenter {
        fun getAnswer(id: Int)
        fun saveAnswer(answer: String, isRight: Boolean)
        fun deleteAnswer(answer: AnswerModel)
    }

    interface View : BaseView, MainFragment {
        fun showAnswer(answer: AnswerModel)
    }
}