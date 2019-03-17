package com.demoss.idp.data.local

import com.demoss.idp.data.local.repository.localRepositoryModule
import com.demoss.idp.data.local.room.dbModule

val localDataModules = listOf(
    dbModule,
    localRepositoryModule
)