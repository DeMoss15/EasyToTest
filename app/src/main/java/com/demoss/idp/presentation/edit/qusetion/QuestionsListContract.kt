package com.demoss.idp.presentation.edit.qusetion

import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.util.pagination.Paginator

interface QuestionsListContract {

    interface Presenter : BasePresenter {
        fun init(testId: Int)
    }

    interface View : BaseView, Paginator.ViewController<QuestionModel> {}
}