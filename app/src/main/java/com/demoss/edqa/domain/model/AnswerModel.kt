package com.demoss.edqa.domain.model

class AnswerModel(
    var id: Int,
    var text: String,
    var isRightAnswer: Boolean,
    var status: EntityStatus = EntityStatus.NEW
) {
    override fun equals(other: Any?): Boolean = other is AnswerModel &&
            other.id == id

    override fun hashCode(): Int = id
}