package com.demoss.idp.data.local.repository.room

import androidx.room.EmptyResultSetException
import com.demoss.idp.data.local.DomainToLocalMapper
import com.demoss.idp.data.local.LocalToDomainMapper
import com.demoss.idp.data.local.db.AppDatabase
import com.demoss.idp.data.local.repository.LocalTestModelRepository
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.util.setDefaultSchedulers
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalTestModelRoomDataSource(val db: AppDatabase) :
    LocalTestModelRepository {

    override fun createTest(test: TestModel): Single<Int> =
        Single.just(test).observeOn(Schedulers.io()).map {
            db.testDao().addTest(DomainToLocalMapper.toLocal(it)).toInt()
        }.setDefaultSchedulers()

    override fun getTests(pageObservable: Observable<Int>): Observable<List<TestModel>> =
        pageObservable.observeOn(Schedulers.io())
            .flatMap { db.testDao().getTestsPaged(it - 1) }
            .map { LocalToDomainMapper.toDomain(it) }
            .setDefaultSchedulers()

    override fun getTest(testId: Int): Single<TestModel> =
        db.testDao().getTest(testId).observeOn(Schedulers.io())
            .map { test ->
                test.apply {
                    questions = db.questionDao().getQuestions(id).map { question ->
                        question.apply { answers = db.answerDao().getAnswers(id) }
                    }
                }
            }.map { LocalToDomainMapper.toDomain(it) }
            .setDefaultSchedulers()

    override fun updateTest(test: TestModel): Completable =
        Completable.fromCallable { db.testDao().updateTest(DomainToLocalMapper.toLocal(test)) }

    override fun removeTest(test: TestModel): Completable =
        Completable.fromCallable { db.testDao().deleteTest(DomainToLocalMapper.toLocal(test)) }
}