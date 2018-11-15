package com.demoss.idp.data.local.repository

import com.demoss.idp.data.local.DomainToLocalMapper
import com.demoss.idp.data.local.LocalToDomainMapper
import com.demoss.idp.data.local.db.AppDatabase
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.util.setDefaultSchedulers
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalTestModelRoomDataSource(val db: AppDatabase) : LocalTestModelRepository {

    override fun createTest(test: TestModel): Completable =
        with(DomainToLocalMapper.toLocal(test)) {
            db.testDao().addTest(this).andThen {
                db.questionDao().addQuestion(questions)
            }.andThen {
                questions.map { question -> db.answerDao().addAnswer(question.answers) }
            }
        }

    override fun getTests(pageObservable: Observable<Int>): Observable<List<TestModel>> =
        pageObservable.observeOn(Schedulers.io())
            .map { LocalToDomainMapper.toDomain(db.testDao().getTestsPaged(it - 1)) }
            .setDefaultSchedulers()

    override fun getTest(testId: Int): Single<TestModel> =
        Single.just(LocalToDomainMapper.toDomain(db.testDao().getTest(testId)))

    override fun updateTest(test: TestModel): Completable =
        with(DomainToLocalMapper.toLocal(test)) {
            db.testDao().updateTest(this).andThen {
                db.questionDao().updateQuestion(questions)
            }.andThen {
                questions.map { question -> db.answerDao().updateAnswer(question.answers) }
            }
        }

    override fun removeTest(test: TestModel): Completable =
        db.testDao().deleteTest(DomainToLocalMapper.toLocal(test))
}