package com.demoss.edqa.data.local

import com.demoss.edqa.data.local.repository.localRepositoryModule
import com.demoss.edqa.data.local.room.dbModule

val localDataModules = listOf(
    dbModule,
    localRepositoryModule
)