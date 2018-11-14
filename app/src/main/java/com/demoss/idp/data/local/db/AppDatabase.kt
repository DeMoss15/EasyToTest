package com.demoss.idp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demoss.idp.data.local.db.dao.AnswerRoomDao
import com.demoss.idp.data.local.db.dao.QuestionRoomDao
import com.demoss.idp.data.local.db.dao.TestRoomDao
import com.demoss.idp.data.local.db.entities.AnswerRoomEntity
import com.demoss.idp.data.local.db.entities.QuestionRoomEntity
import com.demoss.idp.data.local.db.entities.TestRoomEntity

@Database(
    entities = [
        TestRoomEntity::class,
        QuestionRoomEntity::class,
        AnswerRoomEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun testDao(): TestRoomDao
    abstract fun questionDao(): QuestionRoomDao
    abstract fun answerDao(): AnswerRoomDao
}