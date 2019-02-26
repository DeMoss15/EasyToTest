package com.demoss.idp.presentation.main.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.StyleRes
import com.demoss.idp.R
import com.demoss.idp.base.BaseActivity
import com.demoss.idp.util.Constants
import com.demoss.idp.util.EmptyConstants
import de.cketti.mailto.EmailIntentBuilder
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.android.ext.android.inject

class SettingsActivity : BaseActivity<SettingsContract.Presenter>() {

    override val presenter: SettingsContract.Presenter by inject()
    override val layoutResourceId: Int = R.layout.activity_settings

    companion object {
        const val THEME_RED = "Red"
        const val THEME_GREEN = "Green"
        const val THEME_BLUE = "Blue"
        const val THEME_DEEP_ORANGE = "Deep Orange"
        const val THEME_YELLOW = "Yellow"
        const val THEME_BASE = "Standart"

        fun newIntent(context: Context): Intent = Intent(context, SettingsActivity::class.java)
    }

    private val themes = linkedMapOf(
            THEME_BASE to R.style.AppTheme,
            THEME_RED to R.style.AppTheme_Red,
            THEME_GREEN to R.style.AppTheme_Green,
            THEME_DEEP_ORANGE to R.style.AppTheme_DeepOrange,
            THEME_YELLOW to R.style.AppTheme_Yellow,
            THEME_BLUE to R.style.AppTheme_Blue
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        getSharedPrefs()?.apply {
            presenter.currentApplicationTheme = themes.filterValues { it == getInt(Constants.THEME_KEY, R.style.AppTheme) }.keys.first()
        }
        super.onCreate(savedInstanceState)
        spinnerThemes.apply {
            context?.let { adapter = ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, themes.keys.toMutableList()) }
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // nothing
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    setThemeForActivity(themes.keys.elementAt(position))
                }
            }
        }
        spinnerThemes.setSelection(themes.keys.indexOf(presenter.currentApplicationTheme))
        btnSendFeedback.setOnClickListener {
            EmailIntentBuilder.from(this)
                .to("mossur15@gmail.com")
                .subject("Feedback ${application.resources.getString(R.string.app_name)}")
                .start()
        }
        fab.setOnClickListener {
            presenter.currentApplicationTheme = EmptyConstants.EMPTY_STRING
            presenter.saving = true
            setResult(Activity.RESULT_OK)
            finish()
        }
        tvAboutApplication.movementMethod = ScrollingMovementMethod()
    }

    override fun onStop() {
        if (!presenter.saving) themes[presenter.currentApplicationTheme]?.let { putThemeInSharedPrefs(it) }
        super.onStop()
    }

    private fun setThemeForActivity(key: String) {
        getSharedPrefs()?.apply {
            val currentTheme = getInt(Constants.THEME_KEY, R.style.AppTheme)
            if (currentTheme != themes[key]) {
                themes[key]?.let { putThemeInSharedPrefs(it) }
                presenter.saving = true
                recreate()
            }
        }
    }

    private fun putThemeInSharedPrefs(@StyleRes style: Int) {
        getSharedPrefs()?.apply {
            edit().clear().putInt(Constants.THEME_KEY, style).apply()
        }
    }

    private fun getSharedPrefs(): SharedPreferences? =
        getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
}