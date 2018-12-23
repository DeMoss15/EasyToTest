package com.demoss.idp.presentation.main.edit.answer

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.usecase.EditTestUseCase
import io.reactivex.observers.DisposableSingleObserver

class EditAnswerPresenter(private val editTestUseCase: EditTestUseCase) : BasePresenterImpl<EditAnswerContract.View>(),
    EditAnswerContract.Presenter {

    override fun getAnswer(id: Int) = editTestUseCase.getAnswer(getDisposableObserver(), id)

    override fun saveAnswer(answer: String, isRight: Boolean) = editTestUseCase.saveAnswer(answer, isRight)

    override fun deleteAnswer(answer: AnswerModel) = editTestUseCase.deleteAnswer(answer)

    private fun getDisposableObserver(): DisposableSingleObserver<AnswerModel> =
        object : DisposableSingleObserver<AnswerModel>() {
            override fun onSuccess(t: AnswerModel) {
                view?.showAnswer(t)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                view?.showToast(e.localizedMessage)
            }
        }
}