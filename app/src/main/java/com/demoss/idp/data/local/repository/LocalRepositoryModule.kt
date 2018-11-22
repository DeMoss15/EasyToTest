package com.demoss.idp.data.local.repository

import com.demoss.idp.data.local.repository.room.*
import org.koin.dsl.module.module

val localRepositoryModule = module {
    single { LocalTestModelRoomDataSource(get()) as LocalTestModelRepository }
    single { LocalQuestionModelRoomDataSource(get()) as LocalQuestionModelRepository }
    single { LocalAnswerModelRoomDataSource(get()) as LocalAnswerModelRepository }
}