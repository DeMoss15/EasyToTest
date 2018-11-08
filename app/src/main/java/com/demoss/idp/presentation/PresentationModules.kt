package com.demoss.idp.presentation

import com.demoss.idp.presentation.local.localDataModule
import org.koin.dsl.module.Module

val presentationModules = listOf<Module>(
    localDataModule
)