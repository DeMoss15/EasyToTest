package com.demoss.idp.data.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "tests")
data class TestRoomEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    // MetaData
    @ColumnInfo(name = "utid") var utid: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "exam_mode") var examMode: Boolean,
    @ColumnInfo(name = "timer") var timer: Long,
    @ColumnInfo(name = "questionsAmountPerSession") var questionsAmountPerSession: Int,
    // Session
    @ColumnInfo(name = "spentTime") var spentTime: String,
    @ColumnInfo(name = "rightAnswersAmount") var rightAnswersAmount: Int,
    @ColumnInfo(name = "shownQuestionsAmount") var shownQuestionsAmount: Int
) {
    @Ignore
    var questions: List<QuestionRoomEntity> = listOf()
}