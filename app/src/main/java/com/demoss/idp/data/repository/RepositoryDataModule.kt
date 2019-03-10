package com.demoss.idp.data.repository

import com.demoss.idp.domain.gateway.AnswerModelRepository
import com.demoss.idp.domain.gateway.QuestionModelRepository
import com.demoss.idp.domain.gateway.TestModelRepository
import org.koin.dsl.module.module

val repositoryDataModule = module {
    factory { TestModelDataSource(get()) as TestModelRepository }
    factory { QuestionModelDataSource(get()) as QuestionModelRepository }
    factory { AnswerModelDataSource(get()) as AnswerModelRepository }
}