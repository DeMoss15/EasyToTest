package com.demoss.idp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.demoss.idp.data.local.db.entities.QuestionRoomEntity

@Dao
interface QuestionRoomDao {

    @Insert
    fun addQuestion(question: QuestionRoomEntity)

    @Insert
    fun addQuestion(questions: List<QuestionRoomEntity>)

    @Update
    fun updateQuestion(question: QuestionRoomEntity)

    @Update
    fun updateQuestion(questions: List<QuestionRoomEntity>)

    @Delete
    fun deleteQuestion(question: QuestionRoomEntity)

    @Delete
    fun deleteQuestion(questions: List<QuestionRoomEntity>)
}