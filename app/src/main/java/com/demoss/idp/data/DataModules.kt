package com.demoss.idp.data

import com.demoss.idp.data.datasource.repositoryDataModule
import com.demoss.idp.data.local.localDataModules

val dataModules = localDataModules + repositoryDataModule