package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.EntityStatus
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.util.Constants.JSON_FIELD_ANSWERS
import com.demoss.idp.util.Constants.JSON_FIELD_EXAM_MODE
import com.demoss.idp.util.Constants.JSON_FIELD_IS_RIGHT
import com.demoss.idp.util.Constants.JSON_FIELD_NAME
import com.demoss.idp.util.Constants.JSON_FIELD_PASSWORD
import com.demoss.idp.util.Constants.JSON_FIELD_QUESTIONS
import com.demoss.idp.util.Constants.JSON_FIELD_QUESTIONS_AMOUNT
import com.demoss.idp.util.Constants.JSON_FIELD_TIMER
import com.demoss.idp.util.Constants.NEW_ENTITY_ID
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class JsonConverterUseCase {

    fun parse(json: String): TestModel = mapToModel(Gson().fromJson<TestJson>(json, TestJson::class.java))

    fun createJson(test: TestModel): String = Gson().toJson(mapToJson(test))

    private fun mapToJson(test: TestModel): TestJson = TestJson(
            test.name,
            test.questions.map { mapToJson(it) }.toMutableList(),
            test.timer,
            test.password,
            test.questionsAmount,
            test.examMode
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
            testJson.password,
            testJson.timer,
            testJson.questionsAmount,
            testJson.examMode
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
        @field:SerializedName(JSON_FIELD_TIMER)
        var timer: Long,
        @field:SerializedName(JSON_FIELD_PASSWORD)
        var password: String,
        @field:SerializedName(JSON_FIELD_QUESTIONS_AMOUNT)
        var questionsAmount: Int,
        @field:SerializedName(JSON_FIELD_EXAM_MODE)
        var examMode: Boolean
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