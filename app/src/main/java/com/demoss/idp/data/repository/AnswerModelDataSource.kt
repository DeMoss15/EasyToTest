package com.demoss.idp.data.repository

import com.demoss.idp.data.local.repository.LocalAnswerModelRepository
import com.demoss.idp.domain.gateway.AnswerModelRepository
import com.demoss.idp.domain.model.AnswerModel
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