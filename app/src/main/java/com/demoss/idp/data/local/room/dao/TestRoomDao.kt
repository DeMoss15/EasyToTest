@file:Suppress("AndroidUnresolvedRoomSqlReference")

package com.demoss.idp.data.local.room.dao

import androidx.room.*
import com.demoss.idp.data.local.room.entities.TestRoomEntity
import com.demoss.idp.util.Constants.PAGE_SIZE
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface TestRoomDao {

    @Insert
    fun addTest(test: TestRoomEntity): Long

    @Update
    fun updateTest(test: TestRoomEntity)

    @Query("SELECT * FROM tests WHERE id LIKE :testId")
    fun getTest(testId: Int): Single<TestRoomEntity>

    @Query("SELECT count(*) FROM tests WHERE utid LIKE :utid")
    fun getTestsByUTID(utid: String): Single<Int>

    @Query("SELECT * FROM tests")
    fun getTests(): List<TestRoomEntity>

    @Query("SELECT * FROM tests LIMIT :pageNumber * $PAGE_SIZE, $PAGE_SIZE")
    fun getTestsPaged(pageNumber: Int): Observable<List<TestRoomEntity>>

    @Delete
    fun deleteTest(test: TestRoomEntity)
}