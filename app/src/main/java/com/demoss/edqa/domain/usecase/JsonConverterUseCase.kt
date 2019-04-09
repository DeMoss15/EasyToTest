package com.demoss.edqa.domain.usecase

import com.demoss.edqa.domain.model.AnswerModel
import com.demoss.edqa.domain.model.EntityStatus
import com.demoss.edqa.domain.model.QuestionModel
import com.demoss.edqa.domain.model.TestModel
import com.demoss.edqa.util.Constants.NEW_ENTITY_ID
import com.google.gson.Gson

class JsonConverterUseCase {

    fun parse(json: String): TestModel = Gson().fromJson(json, TestModel::class.java)
        .apply { refresh() }

    fun createJson(test: TestModel): String = Gson().toJson(test)

    private fun TestModel.refresh() {
        id = NEW_ENTITY_ID
        status = EntityStatus.NEW
        questions.forEach { it.apply { refresh() } }
    }

    private fun QuestionModel.refresh() {
        id = NEW_ENTITY_ID
        status = EntityStatus.NEW
        answers.forEach { it.apply { refresh() } }
    }

    private fun AnswerModel.refresh() {
        id = NEW_ENTITY_ID
        status = EntityStatus.NEW
    }
}