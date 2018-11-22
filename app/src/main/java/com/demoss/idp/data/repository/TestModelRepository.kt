package com.demoss.idp.data.repository

import com.demoss.idp.domain.model.TestModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface TestModelRepository {

    fun getTests(pageObservable: Observable<Int>): Observable<List<TestModel>>
    fun getTest(testId: Int): Single<TestModel>
    fun createOrUpdateTest(test: TestModel): Completable
    fun removeTest(test: TestModel): Completable
}