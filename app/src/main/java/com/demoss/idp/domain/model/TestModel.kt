package com.demoss.idp.domain.model

import com.demoss.idp.util.Constants

class TestModel(
    var id: Int = Constants.NEW_ENTITY_ID,
    var name: String,
    var questions: MutableList<QuestionModel>,
    var status: EntityStatus = EntityStatus.NEW
) {

    override fun equals(other: Any?): Boolean {
        return if (other == null && other is TestModel) {
            false
        } else {
            other as TestModel
            this.id == other.id
        }
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + questions.hashCode()
        return result
    }
}