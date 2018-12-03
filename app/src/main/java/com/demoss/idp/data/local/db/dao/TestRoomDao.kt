package com.demoss.idp.data.local.db.dao

import androidx.room.*
import com.demoss.idp.data.local.db.entities.*
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface TestRoomDao {

    companion object {
        const val PAGE_SIZE = 20
    }

    @Insert
    fun addTest(test: TestRoomEntity): Long

    @Update
    fun updateTest(test: TestRoomEntity)

    @Query("SELECT * FROM tests WHERE id LIKE :testId")
    fun getTest(testId: Int): Single<TestRoomEntity>

    @Query("SELECT * FROM tests")
    fun getTests(): List<TestRoomEntity>

    @Query("SELECT * FROM tests LIMIT :pageNumber * $PAGE_SIZE, $PAGE_SIZE")
    fun getTestsPaged(pageNumber: Int): Observable<List<TestRoomEntity>>

    @Delete
    fun deleteTest(test: TestRoomEntity)
}