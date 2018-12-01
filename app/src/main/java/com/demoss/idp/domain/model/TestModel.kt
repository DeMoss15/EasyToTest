package com.demoss.idp.domain.model

class TestModel(
    val id: Int = 0,
    var name: String,
    var questions: MutableList<QuestionModel>,
    var status: EntityStatus = EntityStatus.NEW
) {

    override fun equals(other: Any?): Boolean {
        return if (other == null && other is TestModel) {
            false
        } else {
            other as TestModel
            this.name == other.name &&
            this.questions == other.questions
        }
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + questions.hashCode()
        return result
    }
}