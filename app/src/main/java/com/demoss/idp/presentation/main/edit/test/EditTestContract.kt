package com.demoss.idp.presentation.main.edit.test

import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.presentation.main.main.MainFragment

interface EditTestContract {

    interface Presenter : BasePresenter {
        var testId: Int
        fun saveTest(testName: String, examMode: Boolean, timer: Long?, password: String, questionsAmount: Int)
        fun deleteTest()
        fun cancel()
        fun deleteQuestion(question: QuestionModel)
    }

    interface View : BaseView, MainFragment {
        fun showTest(test: TestModel)
        fun navigateBack()
        fun showValidationError()
    }
}