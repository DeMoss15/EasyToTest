package com.demoss.idp.presentation.main.main

interface MainCallback {
    fun nextFragment(currentFragmentTag: String, entityId: Int)
    fun back(currentFragmentTag: String)
}