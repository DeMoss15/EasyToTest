package com.demoss.idp.presentation.main.edit.question

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.usecase.EditTestUseCase
import io.reactivex.disposables.Disposable

class EditQuestionPresenter(private val editTestUseCase: EditTestUseCase)
    : BasePresenterImpl<EditQuestionContract.View>(), EditQuestionContract.Presenter {

    private lateinit var disposable: Disposable

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }

    override fun getQuestion(questionId: Int) {
        disposable = editTestUseCase.getQuestion(questionId).subscribe(
                { view?.showQuestion(it) },
                { view?.showToast(it.localizedMessage) }
        )
    }

    override fun saveQuestion(question: String) = editTestUseCase.saveQuestion(question)

    override fun deleteQuestion() = editTestUseCase.deleteQuestion()

    override fun addAnswer(answer: AnswerModel) = editTestUseCase.saveAnswer(answer)

    override fun deleteAnswer(answer: AnswerModel) = editTestUseCase.deleteAnswer(answer)
}