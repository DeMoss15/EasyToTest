package com.demoss.idp.domain.model

class TestModel(
    var name: String,
    var listOfQuestions: List<QuestionModel>
) {

    override fun equals(other: Any?): Boolean {
        return if (other == null && other is TestModel) {
            false
        } else {
            other as TestModel
            this.name == other.name &&
            this.listOfQuestions == other.listOfQuestions
        }
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + listOfQuestions.hashCode()
        return result
    }
}