package com.demoss.idp.presentation.exam

interface ExamCallback {
    fun nextFragment(currentFragmentTag: String)
    fun back(currentFragmentTag: String)
}