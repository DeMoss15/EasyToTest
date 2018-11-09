package com.demoss.idp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demoss.idp.data.local.db.dao.TestRoomDao
import com.demoss.idp.data.local.db.entities.TestRoomEntity

@Database(entities = [TestRoomEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun testDao(): TestRoomDao
}