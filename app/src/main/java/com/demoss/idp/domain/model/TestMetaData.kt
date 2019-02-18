package com.demoss.idp.domain.model

class TestMetaData (
    val utid: String, // unic test id = testName + testId + password + generated key
    var password: String,
    var examMode: Boolean,
    var timer: Minutes,
    var questionsAmountPerSession: Int
) {
    companion object {
        fun buildUtid(testName: String, password: String, generatedKey: String): String = testName + password + generatedKey
    }
}

typealias Minutes = Long