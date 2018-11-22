package com.demoss.idp.data.local.repository.room

import com.demoss.idp.data.local.DomainToLocalMapper
import com.demoss.idp.data.local.db.dao.QuestionRoomDao
import com.demoss.idp.data.local.repository.LocalQuestionModelRepository
import com.demoss.idp.domain.model.QuestionModel

class LocalQuestionModelRoomDataSource(val db: QuestionRoomDao) :
    LocalQuestionModelRepository {

    override fun createQuestion(testId: Int, vararg questions: QuestionModel) {
        db.addQuestion(*questions.map { DomainToLocalMapper.toLocal(testId, it) }.toTypedArray())
    }

    override fun updateQuestion(testId: Int, vararg questions: QuestionModel) {
        db.updateQuestion(*questions.map { DomainToLocalMapper.toLocal(testId, it) }.toTypedArray())
    }

    override fun deleteQuestion(testId: Int, vararg questions: QuestionModel) {
        db.deleteQuestion(*questions.map { DomainToLocalMapper.toLocal(testId, it) }.toTypedArray())
    }
}