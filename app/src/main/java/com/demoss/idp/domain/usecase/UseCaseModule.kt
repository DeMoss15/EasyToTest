package com.demoss.idp.domain.usecase

import org.koin.dsl.module.module

val useCaseModule = module {
    factory { GetTestsUserCase(get()) }
}