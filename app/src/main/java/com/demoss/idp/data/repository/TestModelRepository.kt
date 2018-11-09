package com.demoss.idp.data.repository

import com.demoss.idp.domain.model.TestModel
import io.reactivex.Completable
import io.reactivex.Observable

interface TestModelRepository {

    fun createTest(test: TestModel): Completable
    fun getTests(pageObservable: Observable<Int>): Observable<List<TestModel>>
    fun updateTest(test: TestModel): Completable
    fun removeTest(test: TestModel): Completable
}