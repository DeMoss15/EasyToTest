package com.demoss.edqa.data.local.room.datasource

import com.demoss.edqa.data.local.DomainToLocalMapper
import com.demoss.edqa.data.local.repository.LocalAnswerModelRepository
import com.demoss.edqa.data.local.room.dao.AnswerRoomDao
import com.demoss.edqa.domain.model.AnswerModel
import com.demoss.edqa.util.setDefaultSchedulers
import io.reactivex.Completable

class LocalAnswerModelRoomDataSource(val db: AnswerRoomDao) :
    LocalAnswerModelRepository {

    override fun createAnswer(questionId: Int, vararg answers: AnswerModel): Completable = Completable.fromCallable {
        with(answers.map { DomainToLocalMapper.toLocal(questionId, it) }) {
            db.addAnswer(*toTypedArray())
        }
    }.setDefaultSchedulers()

    override fun updateAnswer(questionId: Int, vararg answers: AnswerModel): Completable = Completable.fromCallable {
        db.updateAnswer(*answers.map { DomainToLocalMapper.toLocal(questionId, it) }.toTypedArray())
    }

    override fun removeAnswer(questionId: Int, vararg answers: AnswerModel): Completable = Completable.fromCallable {
        db.deleteAnswer(*answers.map { DomainToLocalMapper.toLocal(questionId, it) }.toTypedArray())
    }
}