package com.demoss.idp.data.local.db.dao

import androidx.room.*
import com.demoss.idp.data.local.db.entities.AnswerRoomEntity

@Dao
interface AnswerRoomDao {

    @Query("SELECT * FROM answers WHERE question_id LIKE :questionId")
    fun getAnswers(questionId: Int): List<AnswerRoomEntity>

    @Insert
    fun addAnswer(vararg answers: AnswerRoomEntity)

    @Update
    fun updateAnswer(vararg answers: AnswerRoomEntity)

    @Delete
    fun deleteAnswer(vararg answers: AnswerRoomEntity)
}