package com.demoss.idp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.demoss.idp.data.local.db.entities.AnswerRoomEntity
import io.reactivex.Completable

@Dao
interface AnswerRoomDao {

    @Insert
    fun addAnswer(answer: AnswerRoomEntity): Completable

    @Insert
    fun addAnswer(answers: List<AnswerRoomEntity>): Completable

    @Update
    fun updateAnswer(answer: AnswerRoomEntity): Completable

    @Update
    fun updateAnswer(answers: List<AnswerRoomEntity>): Completable

    @Delete
    fun deleteAnswer(answer: AnswerRoomEntity): Completable

    @Delete
    fun deleteAnswer(answers: List<AnswerRoomEntity>): Completable
}