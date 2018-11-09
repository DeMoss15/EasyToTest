package com.demoss.idp.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "answers")
data class AnswerRoomEntity(
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "is_right") val isRight: Boolean
)