package com.demoss.edqa.data.local

import com.demoss.edqa.data.local.room.entities.AnswerRoomEntity
import com.demoss.edqa.data.local.room.entities.QuestionRoomEntity
import com.demoss.edqa.data.local.room.entities.TestRoomEntity
import com.demoss.edqa.domain.model.AnswerModel
import com.demoss.edqa.domain.model.QuestionModel
import com.demoss.edqa.domain.model.TestMetaData
import com.demoss.edqa.domain.model.TestModel
import com.demoss.edqa.util.Constants
import com.demoss.edqa.util.generateKey

object DomainToLocalMapper {

    // Test =================================================================================
    fun toLocal(test: TestModel): TestRoomEntity {
        return TestRoomEntity(
            test.id,
            test.name,
            if (test.metaData.utid.isNotEmpty()) test.metaData.utid else
            TestMetaData.buildUtid(test.name, test.metaData.password, generateKey(Constants.KEY_LENGTH)),
            test.metaData.password,
            test.metaData.examMode,
            test.metaData.timer,
            test.metaData.questionsAmountPerSession,
            test.sessionResults.spentTime,
            test.sessionResults.rightAnswersAmount,
            test.sessionResults.shownQuestionsAmount
        ).apply { questions = test.questions.map { toLocal(test.id, it) } }
    }

    // Question ==============================================================================
    fun toLocal(testId: Int, question: QuestionModel): QuestionRoomEntity {
        return QuestionRoomEntity(
            question.id,
            testId,
            question.text
        ).apply { answers = question.answers.map { toLocal(question.id, it) } }
    }

    // Answers ================================================================================
    fun toLocal(questionId: Int, answer: AnswerModel): AnswerRoomEntity {
        return AnswerRoomEntity(
            answer.id,
            questionId,
            answer.text,
            answer.isRightAnswer
        )
    }
}