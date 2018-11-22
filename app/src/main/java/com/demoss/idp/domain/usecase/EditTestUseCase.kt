package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import io.reactivex.Single
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import java.lang.RuntimeException

class EditTestUseCase(
        private val getTestUseCase: GetTestUseCase,
        private val deleteTestUseCase: DeleteTestUseCase,
        private val updateTestUseCase: UpdateTestUseCase
) {
    private lateinit var test: TestModel
    private lateinit var question: QuestionModel

    // Test ========================================================================================
    fun getTest(singleObserver: DisposableSingleObserver<TestModel>, testId: Int) {
        getTestUseCase.buildUseCaseObservable(GetTestUseCase.Params(testId))
                .map {
                    // TODO: 21.11.18 handle empty test
                    it.apply { test = it }
                }.subscribe(singleObserver)
    }

    fun updateTest(completableObserver: DisposableCompletableObserver) {
        updateTestUseCase.execute(completableObserver, UpdateTestUseCase.Params(test))
    }

    fun deleteTest(completableObserver: DisposableCompletableObserver) {
        deleteTestUseCase.execute(completableObserver, DeleteTestUseCase.Params(test))
    }

    // Question ====================================================================================
    fun getQuestion(questionId: Int): Single<QuestionModel> =
            Single.just(test.questions.find { it.id == questionId }
                    ?: throw RuntimeException("wrong question id"))

    fun addQuestion(question: QuestionModel) {
        test.questions.add(question)
    }

    fun deleteQuestion(question: QuestionModel) {
        test.questions.remove(question)
    }
}