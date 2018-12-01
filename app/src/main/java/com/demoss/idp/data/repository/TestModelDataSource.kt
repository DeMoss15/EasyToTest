package com.demoss.idp.data.repository

import com.demoss.idp.data.local.repository.LocalTestModelRepository
import com.demoss.idp.domain.model.TestModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class TestModelDataSource(val localRepository: LocalTestModelRepository)
    : TestModelRepository {

    override fun createTest(test: TestModel): Completable {
        return localRepository.createTest(test)
    }

    override fun getTests(pageObservable: Observable<Int>): Observable<List<TestModel>> {
        return localRepository.getTests(pageObservable)
    }

    override fun getTest(testId: Int): Single<TestModel> {
        return localRepository.getTest(testId)
    }

    override fun updateTest(test: TestModel): Completable {
        return localRepository.updateTest(test)
    }

    override fun removeTest(test: TestModel): Completable {
        return localRepository.removeTest(test)
    }
}