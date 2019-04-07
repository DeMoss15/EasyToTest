package com.demoss.edqa.data.local.room.dao

import androidx.room.*
import com.demoss.edqa.data.local.room.entities.QuestionRoomEntity

@Dao
interface QuestionRoomDao {

    @Query("SELECT * FROM questions WHERE test_id LIKE :testId")
    fun getQuestions(testId: Int): List<QuestionRoomEntity>

    @Insert
    fun addQuestion(vararg questions: QuestionRoomEntity): List<Long>

    @Update
    fun updateQuestion(vararg questions: QuestionRoomEntity)

    @Delete
    fun deleteQuestion(vararg questions: QuestionRoomEntity)
}