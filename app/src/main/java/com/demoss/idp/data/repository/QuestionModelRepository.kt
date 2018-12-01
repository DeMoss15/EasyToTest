package com.demoss.idp.data.repository

import com.demoss.idp.domain.model.QuestionModel
import io.reactivex.Completable

interface QuestionModelRepository {
    fun createQuestion(testId: Int, vararg questions: QuestionModel): Completable
    fun updateQuestion(testId: Int, vararg questions: QuestionModel): Completable
    fun removeQuestion(testId: Int, vararg questions: QuestionModel): Completable
}