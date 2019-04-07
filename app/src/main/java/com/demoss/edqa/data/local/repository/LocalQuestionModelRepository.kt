package com.demoss.edqa.data.local.repository

import com.demoss.edqa.domain.model.QuestionModel
import io.reactivex.Completable
import io.reactivex.Single

interface LocalQuestionModelRepository {
    fun createQuestion(testId: Int, vararg questions: QuestionModel): Single<List<Int>>
    fun updateQuestion(testId: Int, vararg questions: QuestionModel): Completable
    fun removeQuestion(testId: Int, vararg questions: QuestionModel): Completable
}