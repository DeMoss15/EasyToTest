package com.demoss.idp.presentation.main.main

interface MainCallback {
    fun nextFragment(currentFragmentTag: String, entityId: Int)
    fun back(currentFragmentTag: String)
    fun readyToSetupAppBar() // FIXME: tis a temple solution fro changing bottom app bar settings
}