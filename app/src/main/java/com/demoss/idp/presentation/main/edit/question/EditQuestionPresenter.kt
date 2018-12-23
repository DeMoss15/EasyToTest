package com.demoss.idp.presentation.main.edit.question

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.usecase.EditTestUseCase
import io.reactivex.observers.DisposableSingleObserver

class EditQuestionPresenter(private val editTestUseCase: EditTestUseCase)
    : BasePresenterImpl<EditQuestionContract.View>(), EditQuestionContract.Presenter {

    override fun getQuestion(questionId: Int) = editTestUseCase.getQuestion(getSingleObserver(), questionId)

    override fun saveQuestion(question: String) = editTestUseCase.saveQuestion(question)

    override fun deleteQuestion() = editTestUseCase.deleteQuestion()

    private fun getSingleObserver(): DisposableSingleObserver<QuestionModel> = object : DisposableSingleObserver<QuestionModel>() {
        override fun onSuccess(t: QuestionModel) {
            view?.showQuestion(t)
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            view?.showToast(e.localizedMessage)
        }
    }
}