package com.demoss.idp.data.local.room.datasource

import com.demoss.idp.data.local.DomainToLocalMapper
import com.demoss.idp.data.local.repository.LocalQuestionModelRepository
import com.demoss.idp.data.local.room.dao.QuestionRoomDao
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.util.setDefaultSchedulers
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalQuestionModelRoomDataSource(val db: QuestionRoomDao) :
    LocalQuestionModelRepository {

    override fun createQuestion(testId: Int, vararg questions: QuestionModel): Single<List<Int>> =
        Single.just(testId to questions).observeOn(Schedulers.io()).map { pair ->
            db.addQuestion(*pair.second.map { DomainToLocalMapper.toLocal(pair.first, it) }.toTypedArray()).map { it.toInt() }
        }.setDefaultSchedulers()

    override fun updateQuestion(testId: Int, vararg questions: QuestionModel): Completable = Completable.fromCallable {
        db.updateQuestion(*questions.map { DomainToLocalMapper.toLocal(testId, it) }.toTypedArray())
    }

    override fun removeQuestion(testId: Int, vararg questions: QuestionModel): Completable = Completable.fromCallable {
        db.deleteQuestion(*questions.map { DomainToLocalMapper.toLocal(testId, it) }.toTypedArray())
    }
}