package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.usecase.model.*
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
    factory { GetTestsUserCase(get()) }
    factory { UpdateTestUseCase(get()) }
    factory { DeleteTestUseCase(get()) }
    factory { UpdateTestSessionResult(get()) }
}