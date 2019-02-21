package com.demoss.idp.presentation.main.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.annotation.StyleRes
import com.demoss.idp.R
import com.demoss.idp.base.BaseActivity
import com.demoss.idp.util.Constants
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.android.ext.android.inject

class SettingsActivity: BaseActivity<SettingsContract.Presenter>() {

    override val presenter: SettingsContract.Presenter by inject()
    override val layoutResourceId: Int = R.layout.activity_settings

    private val themes = listOf(
            R.style.AppTheme_Red,
            R.style.AppTheme_Blue,
            R.style.AppTheme_Green,
            R.style.AppTheme
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spinnerThemes.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                setThemeForActivity(R.style.AppTheme)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setThemeForActivity(themes[position])
            }
        }
        btnSendFeedback.setOnClickListener { /*todo send feedback*/ }
        fab.setOnClickListener { onBackPressed() }
    }

    private fun setThemeForActivity(@StyleRes themeId: Int) {
        getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)?.apply {
            val currentTheme = getInt(Constants.THEME_KEY, R.style.AppTheme)
            edit().clear().putInt(Constants.THEME_KEY, themes[(themes.indexOf(currentTheme) + 1) % themes.size]).apply()
        }
        recreate()
    }
}