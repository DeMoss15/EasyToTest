package com.demoss.idp.data.local.repository

import com.demoss.idp.data.local.DomainToLocalMapper
import com.demoss.idp.data.local.LocalToDomainMapper
import com.demoss.idp.data.local.db.AppDatabase
import com.demoss.idp.domain.model.TestModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class LocalTestModelRoomDataSource(val db: AppDatabase) : LocalTestModelRepository {

    override fun createTest(test: TestModel): Completable =
        Completable.fromCallable { db.testDao().addTest(DomainToLocalMapper.toLocal(test)) }

    override fun getTests(pageObservable: Observable<Int>): Observable<List<TestModel>> =
        pageObservable.map { LocalToDomainMapper.toDomain(db.testDao().getTests()) }

    override fun getTest(testId: Int): Single<TestModel> =
        Single.just(LocalToDomainMapper.toDomain(db.testDao().getTest(testId)))

    override fun updateTest(test: TestModel): Completable =
        Completable.fromCallable { db.testDao().updateTest(DomainToLocalMapper.toLocal(test)) }

    override fun removeTest(test: TestModel): Completable =
        Completable.fromCallable { db.testDao().deleteTest(DomainToLocalMapper.toLocal(test)) }
}