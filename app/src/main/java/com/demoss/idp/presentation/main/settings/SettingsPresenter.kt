package com.demoss.idp.presentation.main.settings

import com.demoss.idp.base.mvp.BasePresenterImpl
import com.demoss.idp.util.EmptyConstants

class SettingsPresenter: SettingsContract.Presenter, BasePresenterImpl<SettingsContract.View>() {
    override var currentApplicationTheme: String = EmptyConstants.EMPTY_STRING
        set(value) {
            if (field == EmptyConstants.EMPTY_STRING || value == EmptyConstants.EMPTY_STRING) field = value
            saving = false
        }
    override var saving: Boolean = false


}