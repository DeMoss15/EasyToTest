package com.demoss.idp.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
data class AnswerRoomEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ForeignKey(
        entity = QuestionRoomEntity::class,
        parentColumns = ["id"],
        childColumns = ["question_id"],
        onUpdate = CASCADE,
        onDelete = CASCADE
    )
    @ColumnInfo(name = "question_id") var questionId: Int,
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "is_right") var isRight: Boolean
)