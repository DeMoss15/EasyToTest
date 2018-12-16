package com.demoss.idp

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        initKoin()
    }

    private fun initKoin() {
        startKoin(this, applicationModule)
    }
}