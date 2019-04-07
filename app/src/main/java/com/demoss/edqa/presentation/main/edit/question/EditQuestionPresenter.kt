package com.demoss.edqa.presentation.main.edit.question

import com.demoss.edqa.base.mvp.BasePresenterImpl
import com.demoss.edqa.domain.model.AnswerModel
import com.demoss.edqa.domain.model.QuestionModel
import com.demoss.edqa.domain.usecase.EditTestUseCase
import com.demoss.edqa.util.Constants.NEW_ENTITY_ID
import io.reactivex.observers.DisposableSingleObserver

class EditQuestionPresenter(private val editTestUseCase: EditTestUseCase) :
    BasePresenterImpl<EditQuestionContract.View>(), EditQuestionContract.Presenter {

    override var questionId: Int = NEW_ENTITY_ID

    override fun onViewShown() {
        super.onViewShown()
        editTestUseCase.getQuestion(getSingleObserver(), questionId)
    }

    override fun saveQuestion(question: String) =
        if (question.isEmpty()) view?.showEmptyDataError() ?: Unit
        else navigationAction { editTestUseCase.saveQuestion(question) }

    override fun deleteQuestion() = navigationAction { editTestUseCase.deleteQuestion() }

    override fun cancel() = navigationAction { editTestUseCase.cancelQuestionEditing() }

    override fun deleteAnswer(answer: AnswerModel) = editTestUseCase.deleteAnswer(answer)

    private inline fun navigationAction(action: () -> Unit) {
        action()
        view?.navigateBack()
    }

    private fun getSingleObserver(): DisposableSingleObserver<QuestionModel> =
        object : DisposableSingleObserver<QuestionModel>() {
            override fun onSuccess(t: QuestionModel) {
                view?.showQuestion(t)
                questionId = t.id
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                view?.showToast(e.localizedMessage)
            }
        }
}