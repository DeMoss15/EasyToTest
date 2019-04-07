package com.demoss.edqa.presentation.main.settings

import com.demoss.edqa.base.mvp.BasePresenter
import com.demoss.edqa.base.mvp.BaseView

interface SettingsContract {

    interface View : BaseView
    interface Presenter : BasePresenter {
        var currentApplicationTheme: String
        var themeAction: ThemeChangeAction
    }
    enum class ThemeChangeAction {
        APPLY_SELECTED,
        SAVE_SELECTED,
        REVERT
    }
}