package com.demoss.idp.data.repository

import com.demoss.idp.domain.model.AnswerModel

interface AnswerModelRepository {
    fun createAnswer(questionId: Int, vararg answers: AnswerModel)
    fun updateAnswer(questionId: Int, vararg answers: AnswerModel)
    fun deleteAnswer(questionId: Int, vararg answers: AnswerModel)
}