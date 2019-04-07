package com.demoss.edqa.domain.usecase

import com.demoss.edqa.domain.model.*
import com.demoss.edqa.util.Constants.JSON_FIELD_ANSWERS
import com.demoss.edqa.util.Constants.JSON_FIELD_EXAM_MODE
import com.demoss.edqa.util.Constants.JSON_FIELD_IS_RIGHT
import com.demoss.edqa.util.Constants.JSON_FIELD_META_DATA
import com.demoss.edqa.util.Constants.JSON_FIELD_NAME
import com.demoss.edqa.util.Constants.JSON_FIELD_PASSWORD
import com.demoss.edqa.util.Constants.JSON_FIELD_QUESTIONS
import com.demoss.edqa.util.Constants.JSON_FIELD_QUESTIONS_AMOUNT
import com.demoss.edqa.util.Constants.JSON_FIELD_TIMER
import com.demoss.edqa.util.Constants.JSON_FIELD_UTID
import com.demoss.edqa.util.Constants.NEW_ENTITY_ID
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class JsonConverterUseCase {

    fun parse(json: String): TestModel = mapToModel(Gson().fromJson<TestJson>(json, TestJson::class.java))

    fun createJson(test: TestModel): String = Gson().toJson(mapToJson(test))

    private fun mapToJson(test: TestModel): TestJson = TestJson(
        test.name,
        test.questions.map { mapToJson(it) }.toMutableList(),
        mapToJson(test.metaData)
    )

    private fun mapToJson(metaData: TestMetaData): TestMetaDataJson = TestMetaDataJson(
        metaData.utid,
        metaData.password,
        metaData.examMode,
        metaData.timer,
        metaData.questionsAmountPerSession
    )

    private fun mapToJson(question: QuestionModel): QuestionJson = QuestionJson(
        question.text,
        question.answers.map { mapToJson(it) }.toMutableList()
    )

    private fun mapToJson(answer: AnswerModel): AnswerJson = AnswerJson(
        answer.text,
        answer.isRightAnswer
    )

    private fun mapToModel(testJson: TestJson): TestModel = TestModel(
        NEW_ENTITY_ID,
        testJson.name,
        testJson.questions.map { mapToModel(it) }.toMutableList(),
        EntityStatus.NEW,
        mapToModel(testJson.metaData),
        TempEntitiesFabric.createEmptySession()
    )

    private fun mapToModel(metaData: TestMetaDataJson): TestMetaData = TestMetaData(
        metaData.utid,
        metaData.password,
        metaData.examMode,
        metaData.timer,
        metaData.questionsAmountPerSession
    )

    private fun mapToModel(questionJson: QuestionJson): QuestionModel = QuestionModel(
        NEW_ENTITY_ID,
        questionJson.text,
        questionJson.answers.map { mapToModel(it) }.toMutableList(),
        EntityStatus.NEW
    )

    private fun mapToModel(answerJson: AnswerJson): AnswerModel = AnswerModel(
        NEW_ENTITY_ID,
        answerJson.text,
        answerJson.isRightAnswer,
        EntityStatus.NEW
    )

    private inner class TestJson(
        @field:SerializedName(JSON_FIELD_NAME)
        var name: String,
        @field:SerializedName(JSON_FIELD_QUESTIONS)
        var questions: MutableList<QuestionJson>,
        @field:SerializedName(JSON_FIELD_META_DATA)
        var metaData: TestMetaDataJson
    )

    private inner class TestMetaDataJson(
        @field:SerializedName(JSON_FIELD_UTID)
        val utid: String,
        @field:SerializedName(JSON_FIELD_PASSWORD)
        var password: String,
        @field:SerializedName(JSON_FIELD_EXAM_MODE)
        var examMode: Boolean,
        @field:SerializedName(JSON_FIELD_TIMER)
        var timer: Long,
        @field:SerializedName(JSON_FIELD_QUESTIONS_AMOUNT)
        var questionsAmountPerSession: Int
    )

    private inner class QuestionJson(
        @field:SerializedName(JSON_FIELD_NAME)
        var text: String,
        @field:SerializedName(JSON_FIELD_ANSWERS)
        val answers: MutableList<AnswerJson>
    )

    private inner class AnswerJson(
        @field:SerializedName(JSON_FIELD_NAME)
        var text: String,
        @field:SerializedName(JSON_FIELD_IS_RIGHT)
        var isRightAnswer: Boolean
    )
}