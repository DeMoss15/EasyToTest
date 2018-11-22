package com.demoss.idp.data.local.repository.room

import com.demoss.idp.data.local.DomainToLocalMapper
import com.demoss.idp.data.local.db.dao.AnswerRoomDao
import com.demoss.idp.data.local.repository.LocalAnswerModelRepository
import com.demoss.idp.domain.model.AnswerModel

class LocalAnswerModelRoomDataSource(val db: AnswerRoomDao) :
    LocalAnswerModelRepository {

    override fun createAnswer(questionId: Int, vararg answers: AnswerModel) {
        db.addAnswer(*answers.map { DomainToLocalMapper.toLocal(questionId, it) }.toTypedArray())
    }

    override fun updateAnswer(questionId: Int, vararg answers: AnswerModel) {
        db.updateAnswer(*answers.map { DomainToLocalMapper.toLocal(questionId, it) }.toTypedArray())
    }

    override fun deleteAnswer(questionId: Int, vararg answers: AnswerModel) {
        db.deleteAnswer(*answers.map { DomainToLocalMapper.toLocal(questionId, it) }.toTypedArray())
    }
}