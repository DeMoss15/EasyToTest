package com.demoss.idp.data.local.repository

import com.demoss.idp.domain.model.QuestionModel
import io.reactivex.Completable

interface LocalQuestionModelRepository {
    fun createQuestion(testId: Int, vararg questions: QuestionModel): Completable
    fun updateQuestion(testId: Int, vararg questions: QuestionModel): Completable
    fun removeQuestion(testId: Int, vararg questions: QuestionModel): Completable
}