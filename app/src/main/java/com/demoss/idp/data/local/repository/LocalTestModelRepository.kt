package com.demoss.idp.data.local.repository

import com.demoss.idp.domain.model.TestModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface LocalTestModelRepository {

    fun createTest(test: TestModel): Completable
    fun getTests(pageObservable: Observable<Int>): Observable<List<TestModel>>
    fun getTest(testId: Int): Single<TestModel>
    fun updateTest(test: TestModel): Completable
    fun removeTest(test: TestModel): Completable
}