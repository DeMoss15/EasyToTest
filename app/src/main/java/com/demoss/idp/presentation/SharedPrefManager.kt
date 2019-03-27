package com.demoss.idp.presentation

import android.content.Context
import android.content.SharedPreferences
import com.demoss.idp.util.Constants

object SharedPrefManager {

    fun putString(context: Context?, key: String, value: String) {
        getPrefs(context)?.edit()
            ?.remove(key)
            ?.putString(key, value)
            ?.apply()
    }

    fun getString(context: Context?, key: String, default: String? = null): String? =
        getPrefs(context)?.getString(key, default)

    fun putInt(context: Context?, key: String, value: Int) {
        getPrefs(context)?.edit()
            ?.remove(key)
            ?.putInt(key, value)
            ?.apply()
    }

    fun getInt(context: Context?, key: String, default: Int): Int? =
        getPrefs(context)?.getInt(key, default)

    private fun getPrefs(context:  Context?): SharedPreferences? = context?.getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
}