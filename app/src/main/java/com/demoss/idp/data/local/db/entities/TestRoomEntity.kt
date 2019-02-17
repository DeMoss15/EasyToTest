package com.demoss.idp.data.local.db.entities

import androidx.room.*

@Entity(tableName = "tests")
data class TestRoomEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "timer") var timer: Long,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "exam_mode") var examMode: Boolean,
    @ColumnInfo(name = "questionsAmount") var questionsAmount: Int
) {
    @Ignore
    var questions: List<QuestionRoomEntity> = listOf()
}