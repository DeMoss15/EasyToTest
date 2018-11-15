package com.demoss.idp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.demoss.idp.data.local.db.entities.QuestionRoomEntity
import io.reactivex.Completable

@Dao
interface QuestionRoomDao {

    @Insert
    fun addQuestion(question: QuestionRoomEntity): Completable

    @Insert
    fun addQuestion(questions: List<QuestionRoomEntity>): Completable

    @Update
    fun updateQuestion(question: QuestionRoomEntity): Completable

    @Update
    fun updateQuestion(questions: List<QuestionRoomEntity>): Completable

    @Delete
    fun deleteQuestion(question: QuestionRoomEntity): Completable

    @Delete
    fun deleteQuestion(questions: List<QuestionRoomEntity>): Completable
}