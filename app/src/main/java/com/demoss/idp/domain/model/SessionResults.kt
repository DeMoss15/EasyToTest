package com.demoss.idp.domain.model

import com.demoss.idp.domain.usecase.TempEntitiesFabric

class SessionResults(
    var spentTime: String,
    var rightAnswersAmount: Int,
    var shownQuestionsAmount: Int
) {
    fun isEmpty(): Boolean = this == TempEntitiesFabric.createEmptySession()

    override fun equals(other: Any?): Boolean = other is SessionResults &&
            spentTime == other.spentTime &&
            shownQuestionsAmount == other.shownQuestionsAmount &&
            rightAnswersAmount == other.rightAnswersAmount

    override fun hashCode(): Int = spentTime.hashCode() * 31 +
            rightAnswersAmount * 31 +
            shownQuestionsAmount
}