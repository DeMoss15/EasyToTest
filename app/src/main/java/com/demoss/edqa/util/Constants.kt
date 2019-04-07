package com.demoss.edqa.util

object Constants {
    const val BASE_URL = "base url"
    const val NEW_ENTITY_ID = 0
    const val PAGE_SIZE = 20
    const val EMPTY_TIMER = "0"
    const val TIME_SEPARATOR = ":"
    const val SECONDS_IN_MINUTE = 60

    const val NEW_TEST: String = "$"
    const val NEW_QUESTION: String = "?"
    const val RIGHT_ANSWER: String = "*"
    const val EMPTY_LINE: String = "+"

    const val DATABASE_NAME = "edqa_room_db"
    const val JSON_PREFIX: String = "edqa.test.JSON"
    const val KEY_LENGTH: Int = 16

    const val JSON_FIELD_META_DATA: String = "metaData"
    const val JSON_FIELD_UTID: String = "utid"
    const val JSON_FIELD_QUESTIONS_AMOUNT: String = "questionsAmount"
    const val JSON_FIELD_EXAM_MODE: String = "examMode"
    const val JSON_FIELD_PASSWORD: String = "password"
    const val JSON_FIELD_TIMER: String = "timer"
    const val JSON_FIELD_NAME: String = "name"
    const val JSON_FIELD_QUESTIONS: String = "questions"
    const val JSON_FIELD_ANSWERS: String = "answers"
    const val JSON_FIELD_IS_RIGHT: String = "isRight"

    const val SP_NAME: String = "demoss.shared_prefs"
    const val THEME_KEY: String = "demoss.edqa.sp.theme"
    const val LANGUAGE: String = "demoss.edqa.sp.language"

    val SUPPORTED_LANGUAGES: List<String> = listOf("en", "ru")
}