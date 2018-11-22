package com.demoss.idp.data.local.repository.room

import com.demoss.idp.data.local.DomainToLocalMapper
import com.demoss.idp.data.local.LocalToDomainMapper
import com.demoss.idp.data.local.db.AppDatabase
import com.demoss.idp.data.local.repository.LocalTestModelRepository
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.util.setDefaultSchedulers
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalTestModelRoomDataSource(val db: AppDatabase) :
    LocalTestModelRepository {

    override fun createTest(test: TestModel): Completable =
        Completable.fromCallable { db.testDao().addTest(DomainToLocalMapper.toLocal(test)) }

    override fun getTests(pageObservable: Observable<Int>): Observable<List<TestModel>> =
        pageObservable.observeOn(Schedulers.io())
            .map { LocalToDomainMapper.toDomain(db.testDao().getTestsPaged(it - 1)) }
            .setDefaultSchedulers()
            .map { emptyList ->
                (0 until 20).map {
                    TestModel(it, "Test $it", mutableListOf())
                }
            }

    override fun getTest(testId: Int): Single<TestModel> =
        Single.just(
            /*LocalToDomainMapper.toDomain(db.testDao().getTest(testId).apply {
                questions = db.questionDao().getQuestions(id).map {
                    it.answers = db.answerDao().getAnswers(it.id)
                    it
                }
            })*/
            TestModel(testId, "Test $testId", mutableListOf(QuestionModel(testId + 1, "QUESTION", mutableListOf())))
        )

    override fun updateTest(test: TestModel): Completable =
        Completable.fromCallable { db.testDao().updateTest(DomainToLocalMapper.toLocal(test)) }

    override fun removeTest(test: TestModel): Completable =
        Completable.fromCallable { db.testDao().deleteTest(DomainToLocalMapper.toLocal(test)) }
}