package com.demoss.idp.data.local.repository.room

import com.demoss.idp.data.local.DomainToLocalMapper
import com.demoss.idp.data.local.db.dao.QuestionRoomDao
import com.demoss.idp.data.local.repository.LocalQuestionModelRepository
import com.demoss.idp.domain.model.QuestionModel
import io.reactivex.Completable

class LocalQuestionModelRoomDataSource(val db: QuestionRoomDao) :
    LocalQuestionModelRepository {

    override fun createQuestion(testId: Int, vararg questions: QuestionModel): Completable = Completable.fromCallable {
        db.addQuestion(*questions.map { DomainToLocalMapper.toLocal(testId, it) }.toTypedArray())
    }

    override fun updateQuestion(testId: Int, vararg questions: QuestionModel): Completable = Completable.fromCallable {
        db.updateQuestion(*questions.map { DomainToLocalMapper.toLocal(testId, it) }.toTypedArray())
    }

    override fun removeQuestion(testId: Int, vararg questions: QuestionModel): Completable = Completable.fromCallable {
        db.deleteQuestion(*questions.map { DomainToLocalMapper.toLocal(testId, it) }.toTypedArray())
    }
}