package com.demoss.edqa

import com.demoss.edqa.data.dataModules
import com.demoss.edqa.domain.domainModules
import com.demoss.edqa.presentation.presentationModules
import org.koin.dsl.module.Module

val applicationModule: List<Module> = presentationModules +
        domainModules +
        dataModules