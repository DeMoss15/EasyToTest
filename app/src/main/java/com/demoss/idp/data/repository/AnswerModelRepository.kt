package com.demoss.idp.data.repository

import com.demoss.idp.domain.model.AnswerModel
import io.reactivex.Completable

interface AnswerModelRepository {
    fun createAnswer(questionId: Int, vararg answers: AnswerModel): Completable
    fun updateAnswer(questionId: Int, vararg answers: AnswerModel): Completable
    fun removeAnswer(questionId: Int, vararg answers: AnswerModel): Completable
}