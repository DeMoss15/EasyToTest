package com.demoss.idp.domain.model

class TestMetaData(
    val utid: String, // unic test id = testName + testId + password + generated key
    var password: String,
    var examMode: Boolean,
    var timer: Minutes,
    var questionsAmountPerSession: Int
) {
    companion object {
        fun buildUtid(testName: String, password: String, generatedKey: String): String = testName + password + generatedKey
    }

    override fun equals(other: Any?): Boolean = other is TestMetaData &&
            utid == other.utid &&
            password == other.utid &&
            examMode == other.examMode &&
            timer == other.timer &&
            questionsAmountPerSession == other.questionsAmountPerSession

    override fun hashCode(): Int = utid.hashCode() * 31 + password.hashCode()
}

typealias Minutes = Long