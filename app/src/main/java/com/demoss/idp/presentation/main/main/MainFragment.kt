package com.demoss.idp.presentation.main.main

import androidx.annotation.IdRes

interface MainFragment {
    fun onFabPressed()
    fun onMenuItemPressed(@IdRes itemId: Int)
}