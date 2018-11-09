package com.demoss.idp.data.local

import com.demoss.idp.data.local.db.entities.AnswerRoomEntity
import com.demoss.idp.data.local.db.entities.QuestionRoomEntity
import com.demoss.idp.data.local.db.entities.TestRoomEntity
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel

object DomainToLocalMapper {

    // Tests ================================================================================
    fun toLocal(tests: List<TestModel>): List<TestRoomEntity> {
        return tests.map { toLocal(it) }
    }

    fun toLocal(test: TestModel): TestRoomEntity {
        return TestRoomEntity(
            test.id,
            test.name,
            test.questions.map { toLocal(it) }
        )
    }

    // Question ==============================================================================
    private fun toLocal(question: QuestionModel): QuestionRoomEntity {
        return QuestionRoomEntity(
            question.text,
            question.answers.map { toLocal(it) }
        )
    }

    // Answers ================================================================================
    private fun toLocal(answer: AnswerModel): AnswerRoomEntity {
        return AnswerRoomEntity(
            answer.text,
            answer.isRightAnswer
        )
    }
}