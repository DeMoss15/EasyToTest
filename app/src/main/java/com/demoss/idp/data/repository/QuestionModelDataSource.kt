package com.demoss.idp.data.repository

import com.demoss.idp.data.local.repository.LocalQuestionModelRepository
import com.demoss.idp.domain.model.QuestionModel

class QuestionModelDataSource(private val localRepository: LocalQuestionModelRepository) : QuestionModelRepository {

    override fun createQuestion(testId: Int, vararg questions: QuestionModel) {
        localRepository.createQuestion(testId, *questions)
    }

    override fun updateQuestion(testId: Int, vararg questions: QuestionModel) {
        localRepository.updateQuestion(testId, *questions)
    }

    override fun deleteQuestion(testId: Int, vararg questions: QuestionModel) {
        localRepository.deleteQuestion(testId, *questions)
    }
}