package com.demoss.edqa.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import com.demoss.edqa.util.Constants
import java.util.*

object LocaleManager {

    fun setLocale(context: Context?): Context? = setNewLocale(context, getLanguage(context))

    fun setNewLocale(context: Context?, language: String): Context? = updateResources(context, language).also {
        persistLanguage(context, language)
    }

    private fun persistLanguage(context: Context?, language: String): Unit =
        SharedPrefManager.putString(context, Constants.LANGUAGE, language)

    private fun getLanguage(context: Context?): String =
        SharedPrefManager.getString(context, Constants.LANGUAGE, Locale.getDefault().language)

    @SuppressLint("ObsoleteSdkInt")
    private fun updateResources(context: Context?, language: String): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val res = context?.resources
        val conf = res?.configuration
        return when {
            Build.VERSION.SDK_INT >= 17 -> {
                conf?.setLocale(locale)
                context?.createConfigurationContext(conf)
            }
            else -> {
                conf?.locale = locale
                res?.updateConfiguration(conf, res.displayMetrics)
                context
            }
        }
    }
}