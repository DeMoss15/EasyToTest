package com.demoss.idp.data.local

import com.demoss.idp.data.local.db.entities.AnswerRoomEntity
import com.demoss.idp.data.local.db.entities.QuestionRoomEntity
import com.demoss.idp.data.local.db.entities.TestRoomEntity
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel

object DomainToLocalMapper {

    // Test =================================================================================
    fun toLocal(test: TestModel): TestRoomEntity {
        return TestRoomEntity(
                test.id,
                test.name,
                test.timer,
                test.password,
                test.examMode,
                test.questionsAmount
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