package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.usecase.model.*
import org.koin.dsl.module.module

val useCaseModule = module {
    single { EditTestUseCase(get(), get()) }
    single { SaveChangesUseCase(get(), get(), get()) }

    factory { CreateTestUseCase(get()) }
    factory { GetTestsUserCase(get()) }
    factory { GetTestUseCase(get()) }
    factory { UpdateTestUseCase(get()) }
    factory { DeleteTestUseCase(get()) }
}