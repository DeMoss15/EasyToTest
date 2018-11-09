package com.demoss.idp.data.local.db.entities

import androidx.room.*

@Entity(tableName = "questions")
data class QuestionRoomEntity(
    @ColumnInfo(name = "content") val content: String,
    @Relation(
        entity = QuestionRoomEntity::class,
        parentColumn = "id",
        entityColumn = "answer_id"
    ) val answers: List<AnswerRoomEntity>
)