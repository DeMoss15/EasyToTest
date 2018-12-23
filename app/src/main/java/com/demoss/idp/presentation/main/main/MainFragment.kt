package com.demoss.idp.presentation.main.main

import androidx.annotation.IdRes
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

interface MainFragment {
    fun onFabPressed()
    fun onMenuItemPressed(@IdRes itemId: Int)
    fun setupAppBar(bottomAppBar: BottomAppBar, fab: FloatingActionButton)
}