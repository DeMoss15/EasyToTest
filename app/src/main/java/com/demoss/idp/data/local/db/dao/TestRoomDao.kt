package com.demoss.idp.data.local.db.dao

import androidx.room.*
import com.demoss.idp.data.local.db.entities.*

@Dao
interface TestRoomDao {

    @Insert
    fun addTest(test: TestRoomEntity)

    @Update
    fun updateTest(test: TestRoomEntity)

    @Query("SELECT count(*) FROM tests")
    fun getTestsTotalCount(): Int

    @Query("SELECT * FROM tests WHERE id LIKE :testId")
    fun getTest(testId: Int): TestRoomEntity

    @Query("SELECT * FROM tests")
    fun getTests(): List<TestRoomEntity>

    @Delete
    fun deleteTest(test: TestRoomEntity)
}