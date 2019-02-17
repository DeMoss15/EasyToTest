package com.demoss.idp.domain.model

class QuestionModel(
    var id: Int,
    var text: String,
    val answers: MutableList<AnswerModel>,
    var status: EntityStatus = EntityStatus.NEW
) {
    override fun equals(other: Any?): Boolean = other is QuestionModel &&
            other.id == id

    override fun hashCode(): Int = id * 31 + answers.hashCode()
}