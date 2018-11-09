package com.demoss.idp.presentation.local

import org.koin.dsl.module.module

val localDataModule = module {
    factory { LocalDataPresenter(get()) as LocalDataContract.Presenter }
}