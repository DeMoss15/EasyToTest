package com.demoss.idp.data.local

import androidx.room.Room
import com.demoss.idp.data.local.db.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val dbModule = module {
    factory {
        Room.databaseBuilder(
            androidApplication().applicationContext,
            AppDatabase::class.java,
            "easy_to_test_db"
        ).build()
    }
}