package com.demoss.idp.presentation.main.edit.test

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.EditTestUseCase
import com.demoss.idp.util.Constants.NEW_ENTITY_ID
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

    override fun saveTest(testName: String) =
        navigationAction { editTestUseCase.saveTest(testName, getCompletableObserver()) }

    override fun deleteTest() = navigationAction { editTestUseCase.deleteTest(getCompletableObserver()) }

    override fun cancel() = navigationAction { editTestUseCase.cancelTestEditing() }

    override fun deleteQuestion(question: QuestionModel) = editTestUseCase.deleteQuestion(question)

    private inline fun navigationAction(action: () -> Unit) {
        action()
        view?.navigateBack()
    }

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