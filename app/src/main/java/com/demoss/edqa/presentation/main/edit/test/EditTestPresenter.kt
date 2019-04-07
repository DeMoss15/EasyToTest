package com.demoss.edqa.presentation.main.edit.test

import com.demoss.edqa.base.mvp.BasePresenterImpl
import com.demoss.edqa.domain.model.QuestionModel
import com.demoss.edqa.domain.model.TestModel
import com.demoss.edqa.domain.usecase.EditTestUseCase
import com.demoss.edqa.util.Constants.NEW_ENTITY_ID
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver

class EditTestPresenter(private val editTestUseCase: EditTestUseCase) : BasePresenterImpl<EditTestContract.View>(),
        EditTestContract.Presenter {

    override var testId: Int = NEW_ENTITY_ID

    override fun onViewShown() {
        super.onViewShown()
        editTestUseCase.getTest(object : DisposableSingleObserver<TestModel>() {
            override fun onSuccess(t: TestModel) {
                view?.showTest(t)
                testId = t.id
            }

            override fun onError(e: Throwable) {
                view?.showToast(e.localizedMessage)
            }
        }, testId)
    }

    override fun saveTest(testName: String, examMode: Boolean, timer: Long?, password: String, questionsAmount: Int) {
        if (validateInput(testName, examMode, timer, questionsAmount, password)) {
            navigationAction {
                editTestUseCase.saveTest(testName, examMode, password, timer
                        ?: 0L, questionsAmount, getCompletableObserver())
            }
        } else {
            view?.showValidationError()
        }
    }

    override fun deleteTest() = navigationAction { editTestUseCase.deleteTest(getCompletableObserver()) }

    override fun cancel() = navigationAction { editTestUseCase.cancelTestEditing() }

    override fun deleteQuestion(question: QuestionModel) = editTestUseCase.deleteQuestion(question)

    private inline fun navigationAction(action: () -> Unit) {
        action()
        view?.navigateBack()
    }

    private fun validateInput(name: String, examMode: Boolean, timer: Long?, questionsAmount: Int, password: String): Boolean = name.isNotEmpty() &&
            ((examMode && timer != null && timer != 0L && questionsAmount != 0 && password.isNotEmpty()) || !examMode)

    private fun getCompletableObserver(): DisposableCompletableObserver =
            object : DisposableCompletableObserver() {
                override fun onComplete() {
                    view?.navigateBack()
                }

                override fun onError(e: Throwable) {
                    view?.showToast(e.localizedMessage)
                }
            }
}