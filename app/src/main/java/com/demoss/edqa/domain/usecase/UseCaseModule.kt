package com.demoss.edqa.domain.usecase

import com.demoss.edqa.domain.usecase.model.*
import org.koin.dsl.module.module

val useCaseModule = module {
    single { EditTestUseCase(get(), get()) }
    single { TestSessionUseCase(get(), get()) }

    factory { SaveChangesUseCase(get(), get(), get()) }
    factory { ParseFileUseCase(get(), get(), get()) }
    factory { ShareTestUseCase(get(), get(), get()) }

    factory { JsonConverterUseCase() }
    factory { EncryptionUseCase() }

    factory { CreateTestUseCase(get()) }
    factory { GetTestUseCase(get()) }
    factory { GetTestsUseCase(get()) }
    factory { UpdateTestUseCase(get()) }
    factory { DeleteTestUseCase(get()) }
    factory { UpdateTestSessionResult(get()) }
}