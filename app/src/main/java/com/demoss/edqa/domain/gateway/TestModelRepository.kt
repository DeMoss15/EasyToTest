package com.demoss.edqa.domain.gateway

import com.demoss.edqa.domain.model.TestModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface TestModelRepository {

    fun createTest(test: TestModel): Single<Int>
    fun getTests(pageObservable: Observable<Int>): Observable<List<TestModel>>
    fun getTest(testId: Int): Single<TestModel>
    fun updateTest(test: TestModel): Completable
    fun removeTest(test: TestModel): Completable
}