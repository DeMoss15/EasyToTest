package com.demoss.idp.domain.model

import com.demoss.idp.util.Constants

class TestModel(
    var id: Int = Constants.NEW_ENTITY_ID,
    var name: String,
    var questions: MutableList<QuestionModel>,
    var status: EntityStatus = EntityStatus.NEW,
    var password: String,
        // session
    var timer: Long,
    var questionsAmount: Int,
        // exam mode
    /** NOTE:
     * if example mode enabled session and password are required
     **/
    var examMode: Boolean
) {

    override fun equals(other: Any?): Boolean = other is TestModel &&
            this.id == other.id &&
            this.password == other.password

    override fun hashCode(): Int = id * 31 + questions.hashCode()
}