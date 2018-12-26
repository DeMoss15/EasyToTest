package com.demoss.idp.domain.model

class AnswerModel(
    var id: Int,
    var text: String,
    var isRightAnswer: Boolean,
    var status: EntityStatus = EntityStatus.NEW
)