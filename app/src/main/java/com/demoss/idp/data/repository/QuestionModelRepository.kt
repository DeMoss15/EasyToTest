package com.demoss.idp.data.repository

import com.demoss.idp.domain.model.QuestionModel

interface QuestionModelRepository {
    fun createQuestion(testId: Int, vararg questions: QuestionModel)
    fun updateQuestion(testId: Int, vararg questions: QuestionModel)
    fun deleteQuestion(testId: Int, vararg questions: QuestionModel)
}