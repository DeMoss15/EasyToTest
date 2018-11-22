package com.demoss.idp.data.local.repository

import com.demoss.idp.domain.model.AnswerModel

interface LocalAnswerModelRepository {
    fun createAnswer(questionId: Int, vararg answers: AnswerModel)
    fun updateAnswer(questionId: Int, vararg answers: AnswerModel)
    fun deleteAnswer(questionId: Int, vararg answers: AnswerModel)
}