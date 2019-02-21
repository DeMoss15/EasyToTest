package com.demoss.idp.domain.model

import com.demoss.idp.util.Constants

class TestModel(
    var id: Int = Constants.NEW_ENTITY_ID,
    var name: String,
    var questions: MutableList<QuestionModel>,
    var status: EntityStatus = EntityStatus.NEW,
    var metaData: TestMetaData,
    var sessionResults: SessionResults
) {

    override fun equals(other: Any?): Boolean = other is TestModel &&
            id == other.id &&
            name == other.name &&
            questions == other.questions &&
            metaData == other.metaData &&
            sessionResults == other.sessionResults

    override fun hashCode(): Int = id * 31 +
            questions.hashCode() * 31 +
            metaData.hashCode() * 31 +
            sessionResults.hashCode()
}