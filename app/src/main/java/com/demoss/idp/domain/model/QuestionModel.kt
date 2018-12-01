package com.demoss.idp.domain.model

class QuestionModel(
    val id: Int,
    var text: String,
    val answers: MutableList<AnswerModel>,
    var status: EntityStatus = EntityStatus.NEW
)