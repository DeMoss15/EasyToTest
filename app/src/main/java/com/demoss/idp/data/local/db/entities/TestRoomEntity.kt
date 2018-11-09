package com.demoss.idp.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "tests")
data class TestRoomEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @Relation(
        entity = QuestionRoomEntity::class,
        parentColumn = "id",
        entityColumn = "question_id"
    ) val questions: List<QuestionRoomEntity>
)