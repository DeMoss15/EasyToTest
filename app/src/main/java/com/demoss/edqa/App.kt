package com.demoss.edqa

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.demoss.edqa.presentation.LocaleManager
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.setLocale(this)
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        initKoin()
    }

    private fun initKoin() {
        startKoin(this, applicationModule)
    }
}