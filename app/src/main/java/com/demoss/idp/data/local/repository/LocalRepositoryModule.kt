package com.demoss.idp.data.local.repository

import org.koin.dsl.module.module

val localRepositoryModule = module {
    factory { LocalTestModelRoomDataSource(get()) as LocalTestModelRepository }
}