package com.demoss.idp.presentation.main.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.StyleRes
import com.demoss.idp.R
import com.demoss.idp.base.BaseActivity
import com.demoss.idp.presentation.LocaleManager
import com.demoss.idp.presentation.SharedPrefManager
import com.demoss.idp.util.Constants
import de.cketti.mailto.EmailIntentBuilder
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.android.ext.android.inject

class SettingsActivity : BaseActivity<SettingsContract.Presenter>() {

    override val presenter: SettingsContract.Presenter by inject()
    override val layoutResourceId: Int = R.layout.activity_settings

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, SettingsActivity::class.java)
    }

    private val themes by lazy {
        linkedMapOf(
            getString(R.string.theme_default) to R.style.AppTheme,
            getString(R.string.theme_red) to R.style.AppTheme_Red,
            getString(R.string.theme_green) to R.style.AppTheme_Green,
            getString(R.string.theme_deep_orange) to R.style.AppTheme_DeepOrange,
            getString(R.string.theme_yellow) to R.style.AppTheme_Yellow,
            getString(R.string.theme_blue) to R.style.AppTheme_Blue
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        SharedPrefManager.getInt(this, Constants.THEME_KEY, R.style.AppTheme).let { currentTheme ->
            presenter.currentApplicationTheme =
                themes.filterValues { it == currentTheme }
                    .keys.first()
        }
        super.onCreate(savedInstanceState)
        spinnerThemes.apply {
            context?.let {
                adapter = ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, themes.keys.toMutableList())
            }
            setSelection(themes.keys.indexOf(presenter.currentApplicationTheme))
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // nothing
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    applyThemeForTheActivity(themes.keys.elementAt(position))
                }
            }
        }
        btnSendFeedback.setOnClickListener {
            LocaleManager.setNewLocale(applicationContext, "ru")
            recreate()
            //sendEmail()
        }
        fab.setOnClickListener {
            presenter.themeAction = SettingsContract.ThemeChangeAction.SAVE_SELECTED
            setResult(Activity.RESULT_OK)
            finish()
        }
        tvAboutApplication.text = Html.fromHtml(getString(R.string.about_application))
    }

    override fun onStop() {
        if (presenter.themeAction == SettingsContract.ThemeChangeAction.REVERT)
            themes[presenter.currentApplicationTheme]?.let { putThemeInSharedPrefs(it) }
        super.onStop()
    }

    private fun sendEmail() {
        EmailIntentBuilder.from(this)
            .to(getString(R.string.application_author_email))
            .subject(getString(R.string.feedback_title, getString(R.string.app_name)))
            .start()
    }

    private fun applyThemeForTheActivity(key: String) {
        val currentTheme = SharedPrefManager.getInt(this, Constants.THEME_KEY, R.style.AppTheme)
        if (currentTheme != themes[key]) {
            themes[key]?.let { putThemeInSharedPrefs(it) }
            presenter.themeAction = SettingsContract.ThemeChangeAction.APPLY_SELECTED
            recreate()
        }
    }

    private fun putThemeInSharedPrefs(@StyleRes style: Int) =
        SharedPrefManager.putInt(this, Constants.THEME_KEY, style)
}