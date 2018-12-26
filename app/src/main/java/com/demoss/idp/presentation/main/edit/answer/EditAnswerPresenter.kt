package com.demoss.idp.presentation.main.edit.answer

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.usecase.EditTestUseCase
import com.demoss.idp.util.Constants.NEW_ENTITY_ID
import io.reactivex.observers.DisposableSingleObserver

class EditAnswerPresenter(private val editTestUseCase: EditTestUseCase) : BasePresenterImpl<EditAnswerContract.View>(),
    EditAnswerContract.Presenter {

    override var answerId: Int = NEW_ENTITY_ID

    override fun onViewShown() {
        super.onViewShown()
        editTestUseCase.getAnswer(getDisposableObserver(), answerId)
    }

    override fun saveAnswer(answer: String, isRight: Boolean) = editTestUseCase.saveAnswer(answer, isRight)

    override fun deleteAnswer(answer: AnswerModel) = editTestUseCase.deleteAnswer(answer)

    private fun getDisposableObserver(): DisposableSingleObserver<AnswerModel> =
        object : DisposableSingleObserver<AnswerModel>() {
            override fun onSuccess(t: AnswerModel) {
                view?.showAnswer(t)
                answerId = t.id
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                view?.showToast(e.localizedMessage)
            }
        }
}