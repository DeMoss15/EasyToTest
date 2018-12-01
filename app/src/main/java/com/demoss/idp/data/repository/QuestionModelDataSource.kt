package com.demoss.idp.data.repository

import com.demoss.idp.data.local.repository.LocalQuestionModelRepository
import com.demoss.idp.domain.model.QuestionModel
import io.reactivex.Completable

class QuestionModelDataSource(private val localRepository: LocalQuestionModelRepository) : QuestionModelRepository {

    override fun createQuestion(testId: Int, vararg questions: QuestionModel): Completable =
        localRepository.createQuestion(testId, *questions)

    override fun updateQuestion(testId: Int, vararg questions: QuestionModel): Completable =
        localRepository.updateQuestion(testId, *questions)

    override fun removeQuestion(testId: Int, vararg questions: QuestionModel): Completable =
        localRepository.removeQuestion(testId, *questions)
}