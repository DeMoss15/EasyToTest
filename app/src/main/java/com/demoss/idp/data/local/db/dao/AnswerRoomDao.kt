package com.demoss.idp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.demoss.idp.data.local.db.entities.AnswerRoomEntity

@Dao
interface AnswerRoomDao {

    @Insert
    fun addAnswer(answer: AnswerRoomEntity)

    @Insert
    fun addAnswer(answers: List<AnswerRoomEntity>)

    @Update
    fun updateAnswer(answer: AnswerRoomEntity)

    @Update
    fun updateAnswer(answers: List<AnswerRoomEntity>)

    @Delete
    fun deleteAnswer(answer: AnswerRoomEntity)

    @Delete
    fun deleteAnswer(answers: List<AnswerRoomEntity>)}