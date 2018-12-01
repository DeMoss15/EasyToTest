package com.demoss.idp.data.repository

import org.koin.dsl.module.module

val repositoryDataModule = module {
    factory { TestModelDataSource(get()) as TestModelRepository }
    factory { QuestionModelDataSource(get()) as QuestionModelRepository }
    factory { AnswerModelDataSource(get()) as AnswerModelRepository }
}