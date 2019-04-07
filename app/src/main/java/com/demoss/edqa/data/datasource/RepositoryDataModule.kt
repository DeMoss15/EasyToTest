package com.demoss.edqa.data.datasource

import com.demoss.edqa.domain.gateway.AnswerModelRepository
import com.demoss.edqa.domain.gateway.QuestionModelRepository
import com.demoss.edqa.domain.gateway.TestModelRepository
import org.koin.dsl.module.module

val repositoryDataModule = module {
    factory { TestModelDataSource(get()) as TestModelRepository }
    factory { QuestionModelDataSource(get()) as QuestionModelRepository }
    factory { AnswerModelDataSource(get()) as AnswerModelRepository }
}