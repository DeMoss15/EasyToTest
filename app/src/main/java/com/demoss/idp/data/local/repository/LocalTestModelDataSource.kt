package com.demoss.idp.data.local.repository

import com.demoss.idp.domain.model.TestModel
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class LocalTestModelDataSource : LocalTestModelRepository {

    // TODO: 09.11.18 add database and CRUD for it

    override fun createTest(test: TestModel): Completable {
        return Completable.fromCallable { /*some action with database*/ }
    }

    override fun getTests(pageObservable: Observable<Int>): Observable<List<TestModel>> {
        return pageObservable.map {
            val data = mutableListOf<TestModel>()
            for (i in 0..12) {
                data.add(TestModel("test $it,$i", listOf()))
            }
            data.toList()
        }.delay(2, TimeUnit.SECONDS)
    }

    override fun updateTest(test: TestModel): Completable {
        return Completable.fromCallable { /*some action with database*/ }

    }

    override fun removeTest(test: TestModel): Completable {
        return Completable.fromCallable { /*some action with database*/ }
    }
}