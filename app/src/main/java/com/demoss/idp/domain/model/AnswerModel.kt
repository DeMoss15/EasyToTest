package com.demoss.idp.domain.model

class AnswerModel(
    val id: Int,
    var text: String,
    var isRightAnswer: Boolean,
    var status: EntityStatus = EntityStatus.UNMODIFIED
)