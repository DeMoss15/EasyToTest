package com.demoss.idp.domain.model

class QuestionModel(
    val id: Int,
    var text: String,
    val answers: List<AnswerModel>
)