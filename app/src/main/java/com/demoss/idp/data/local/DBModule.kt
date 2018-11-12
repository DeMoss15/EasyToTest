package com.demoss.idp.data.local

import androidx.room.Room
import com.demoss.idp.data.local.db.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val dbModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "db")
            .build()
    }
}