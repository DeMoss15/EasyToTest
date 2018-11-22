package com.demoss.idp.data.local.db.dao

import androidx.room.*
import com.demoss.idp.data.local.db.entities.QuestionRoomEntity

@Dao
interface QuestionRoomDao {

    @Query("SELECT * FROM questions WHERE test_id LIKE :testId")
    fun getQuestions(testId: Int): List<QuestionRoomEntity>

    @Insert
    fun addQuestion(vararg questions: QuestionRoomEntity)

    @Update
    fun updateQuestion(vararg questions: QuestionRoomEntity)

    @Delete
    fun deleteQuestion(vararg questions: QuestionRoomEntity)
}