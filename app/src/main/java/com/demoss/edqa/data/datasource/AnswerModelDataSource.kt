package com.demoss.edqa.data.datasource

import com.demoss.edqa.data.local.repository.LocalAnswerModelRepository
import com.demoss.edqa.domain.gateway.AnswerModelRepository
import com.demoss.edqa.domain.model.AnswerModel
import io.reactivex.Completable

class AnswerModelDataSource(private val localRepository: LocalAnswerModelRepository) :
    AnswerModelRepository {

    override fun createAnswer(questionId: Int, vararg answers: AnswerModel): Completable =
        localRepository.createAnswer(questionId, *answers)

    override fun updateAnswer(questionId: Int, vararg answers: AnswerModel): Completable =
        localRepository.updateAnswer(questionId, *answers)

    override fun removeAnswer(questionId: Int, vararg answers: AnswerModel): Completable =
        localRepository.removeAnswer(questionId, *answers)
}