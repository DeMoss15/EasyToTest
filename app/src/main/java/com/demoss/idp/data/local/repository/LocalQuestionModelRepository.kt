package com.demoss.idp.data.local.repository

import com.demoss.idp.domain.model.QuestionModel

interface LocalQuestionModelRepository {
    fun createQuestion(testId: Int, vararg questions: QuestionModel)
    fun updateQuestion(testId: Int, vararg questions: QuestionModel)
    fun deleteQuestion(testId: Int, vararg questions: QuestionModel)
}