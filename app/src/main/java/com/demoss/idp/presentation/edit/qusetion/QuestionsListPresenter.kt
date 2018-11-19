package com.demoss.idp.presentation.edit.qusetion

import com.demoss.idp.base.mvp.BasePresenterImpl

class QuestionsListPresenter: BasePresenterImpl<QuestionsListContract.View>(), QuestionsListContract.Presenter {

    private var testId = 0

    override fun init(testId: Int) {
        this.testId = testId
    }
}