package com.demoss.edqa.data

import com.demoss.edqa.data.datasource.repositoryDataModule
import com.demoss.edqa.data.local.localDataModules

val dataModules = localDataModules + repositoryDataModule