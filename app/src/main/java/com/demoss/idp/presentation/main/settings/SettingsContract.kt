package com.demoss.idp.presentation.main.settings

import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView

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