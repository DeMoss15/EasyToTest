package com.demoss.idp.data.repository

import com.demoss.idp.data.local.repository.LocalAnswerModelRepository
import com.demoss.idp.domain.model.AnswerModel

class AnswerModelDataSource(private val localRepository: LocalAnswerModelRepository) : AnswerModelRepository {

    override fun createAnswer(questionId: Int, vararg answers: AnswerModel) {
        localRepository.createAnswer(questionId, *answers)
    }

    override fun updateAnswer(questionId: Int, vararg answers: AnswerModel) {
        localRepository.updateAnswer(questionId, *answers)
    }

    override fun deleteAnswer(questionId: Int, vararg answers: AnswerModel) {
        localRepository.deleteAnswer(questionId, *answers)
    }
}