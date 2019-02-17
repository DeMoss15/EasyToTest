package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.EntityStatus
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.util.Constants.NEW_ENTITY_ID
import com.demoss.idp.util.EmptyConstants

object TempEntitiesFabric {

    private var tempIdIterator = -1

    fun cleanTempIds(test: TestModel) {
        if (test.id < 0) test.id = NEW_ENTITY_ID
        test.questions.map { question ->
            if (question.id < 0) question.id = NEW_ENTITY_ID
            question.answers.map { answer -> if (answer.id < 0) answer.id = NEW_ENTITY_ID }
        }
    }

    fun createTempTest(): TestModel =
            TestModel(
                    id = tempIdIterator--,
                    name = EmptyConstants.EMPTY_STRING,
                    questions = mutableListOf(),
                    status = EntityStatus.NEW,
                    timer = 0L,
                    password = EmptyConstants.EMPTY_STRING,
                    questionsAmount = 0,
                    examMode = false
            )

    fun createTempQuestion(): QuestionModel =
            QuestionModel(
                    id = tempIdIterator--,
                    text = EmptyConstants.EMPTY_STRING,
                    answers = mutableListOf(),
                    status = EntityStatus.NEW
            )

    fun createTempAnswer(): AnswerModel =
            AnswerModel(
                    id = tempIdIterator--,
                    text = EmptyConstants.EMPTY_STRING,
                    isRightAnswer = false,
                    status = EntityStatus.NEW
            )
}

fun TestModel.isTemp(): Boolean = this.id < 0
fun QuestionModel.isTemp(): Boolean = this.id < 0
fun AnswerModel.isTemp(): Boolean = this.id < 0
