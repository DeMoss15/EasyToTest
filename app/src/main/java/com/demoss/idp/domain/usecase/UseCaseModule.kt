package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.usecase.model.*
import org.koin.dsl.module.module

val useCaseModule = module {
    single { EditTestUseCase(get(), get()) }
    single { SaveChangesUseCase(get(), get(), get()) }

    single { TestSessionUseCase(get()) }

    factory { CreateTestUseCase(get()) }
    factory { GetTestsUserCase(get()) }
    factory { GetTestUseCase(get()) }
    factory { UpdateTestUseCase(get()) }
    factory { DeleteTestUseCase(get()) }
    factory { ParseFileUseCase(get(), get(), get()) }
    factory { JsonConverterUseCase() }
    factory { EncryptionUseCase() }
    factory { ShareTestUseCase(get(), get(), get()) }
}