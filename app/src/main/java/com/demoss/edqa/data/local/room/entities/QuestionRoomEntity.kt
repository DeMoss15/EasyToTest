package com.demoss.edqa.data.local.room.entities

import androidx.room.*

@Entity(tableName = "questions")
data class QuestionRoomEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ForeignKey(
        entity = TestRoomEntity::class,
        parentColumns = ["id"],
        childColumns = ["test_id"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )
    @ColumnInfo(name = "test_id") var testId: Int,
    @ColumnInfo(name = "content") var content: String
) {
    @Ignore
    var answers: List<AnswerRoomEntity> = listOf()
}