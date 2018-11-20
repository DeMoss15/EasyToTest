package com.demoss.idp.data.local

import com.demoss.idp.data.local.db.entities.AnswerRoomEntity
import com.demoss.idp.data.local.db.entities.QuestionRoomEntity
import com.demoss.idp.data.local.db.entities.TestRoomEntity
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel

object LocalToDomainMapper {

    // Tests ================================================================================
    fun toDomain(tests: List<TestRoomEntity>): List<TestModel> {
        return tests.map { toDomain(it) }
    }

    fun toDomain(test: TestRoomEntity): TestModel {
        return TestModel(
            test.id,
            test.name,
            test.questions.map { toDomain(it) }
        )
    }

    // Question ==============================================================================
    private fun toDomain(question: QuestionRoomEntity): QuestionModel {
        return QuestionModel(
            question.id,
            question.content,
            question.answers.map { toDomain(it) }
        )
    }

    // Answers ================================================================================
    private fun toDomain(answer: AnswerRoomEntity): AnswerModel {
        return AnswerModel(
            answer.id,
            answer.content,
            answer.isRight
        )
    }
}