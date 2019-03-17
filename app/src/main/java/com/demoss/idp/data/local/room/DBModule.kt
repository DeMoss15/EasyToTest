package com.demoss.idp.data.local.room

import androidx.room.Room
import com.demoss.idp.util.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val dbModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, Constants.DATABASE_NAME)
            .build()/*.also {
                Log.i("backup", it.openHelper.writableDatabase.path)
            }*/
    }

    single { get<AppDatabase>().answerDao() }
    single { get<AppDatabase>().questionDao() }
    single { get<AppDatabase>().testDao() }
}