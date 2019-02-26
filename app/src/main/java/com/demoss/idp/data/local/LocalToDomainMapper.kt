package com.demoss.idp.data.local

import com.demoss.idp.data.local.db.entities.AnswerRoomEntity
import com.demoss.idp.data.local.db.entities.QuestionRoomEntity
import com.demoss.idp.data.local.db.entities.TestRoomEntity
import com.demoss.idp.domain.model.*

object LocalToDomainMapper {

    // Tests ================================================================================
    fun toDomain(tests: List<TestRoomEntity>): List<TestModel> {
        return tests.map { toDomain(it) }
    }

    fun toDomain(test: TestRoomEntity): TestModel {
        return TestModel(
            test.id,
            test.name,
            test.questions.map { toDomain(it) }.toMutableList(),
            EntityStatus.SAVED,
            TestMetaData(
                test.utid,
                test.password,
                test.examMode,
                test.timer,
                test.questionsAmountPerSession
            ),
            SessionResults(
                test.spentTime,
                test.rightAnswersAmount,
                test.shownQuestionsAmount
            )
        )
    }

    // Question ==============================================================================
    fun toDomain(question: QuestionRoomEntity): QuestionModel {
        return QuestionModel(
            question.id,
            question.content,
            question.answers.map { toDomain(it) }.toMutableList(),
            EntityStatus.SAVED
        )
    }

    // Answers ================================================================================
    fun toDomain(answer: AnswerRoomEntity): AnswerModel {
        return AnswerModel(
            answer.id,
            answer.content,
            answer.isRight,
            EntityStatus.SAVED
        )
    }
}