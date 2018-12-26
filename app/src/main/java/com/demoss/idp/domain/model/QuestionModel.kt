package com.demoss.idp.domain.model

class QuestionModel(
    var id: Int,
    var text: String,
    val answers: MutableList<AnswerModel>,
    var status: EntityStatus = EntityStatus.NEW
)