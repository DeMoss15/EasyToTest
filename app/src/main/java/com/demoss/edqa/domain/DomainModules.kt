package com.demoss.edqa.domain

import com.demoss.edqa.domain.model.modelModule
import com.demoss.edqa.domain.usecase.useCaseModule

val domainModules = listOf(
    useCaseModule,
    modelModule
)