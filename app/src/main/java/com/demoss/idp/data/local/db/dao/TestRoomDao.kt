package com.demoss.idp.data.local.db.dao

import androidx.room.*
import com.demoss.idp.data.local.db.entities.AnswerRoomEntity
import com.demoss.idp.data.local.db.entities.QuestionRoomEntity
import com.demoss.idp.data.local.db.entities.TestRoomEntity

@Dao
interface TestRoomDao {

    // Tests ==================================================
    @Insert
    fun addTest(test: TestRoomEntity)

    @Update
    fun updateTest(test: TestRoomEntity)

    @Query("SELECT count(*) FROM tests")
    fun getTestsTotalCount(): Int

    @Query("SELECT 1 FROM tests WHERE id LIKE :testId")
    fun getTest(testId: Int): TestRoomEntity

    @Query("SELECT * FROM tests")
    fun getTests(): List<TestRoomEntity>

    @Delete
    fun deleteTest(test: TestRoomEntity)

    // Questions ===============================================
    @Insert
    fun addQuery(query: Query)

    @Update
    fun updateQuestion(question: QuestionRoomEntity)

    @Delete
    fun deleteQuestion(question: QuestionRoomEntity)

    // Questions ===============================================
    @Insert
    fun addAnswer(query: Query)

    @Update
    fun updateAnswer(answer: AnswerRoomEntity)

    @Delete
    fun deleteAnswer(answer: AnswerRoomEntity)
}