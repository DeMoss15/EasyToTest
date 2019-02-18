package com.demoss.idp.domain.model

class SessionResults(
    val spentTime: Minutes,
    val rightAnswersAmount: Int,
    val shownQuestionsAmount: Int
) {
    fun isEmpty(): Boolean = spentTime == 0L && rightAnswersAmount == 0 && shownQuestionsAmount == 0
}