package com.demoss.idp.data.repository

import com.demoss.idp.domain.model.QuestionModel
import io.reactivex.Completable
import io.reactivex.Single

interface QuestionModelRepository {
    fun createQuestion(testId: Int, vararg questions: QuestionModel): Single<List<Int>>
    fun updateQuestion(testId: Int, vararg questions: QuestionModel): Completable
    fun removeQuestion(testId: Int, vararg questions: QuestionModel): Completable
}