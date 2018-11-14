package com.demoss.idp.data.local.db.entities

import androidx.room.*

@Entity(tableName = "tests")
data class TestRoomEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String
) {
    @Ignore
    lateinit var questions: List<QuestionRoomEntity>
}