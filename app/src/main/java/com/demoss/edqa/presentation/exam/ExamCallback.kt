package com.demoss.edqa.presentation.exam

interface ExamCallback {
    fun nextFragment(currentFragmentTag: String)
    fun back(currentFragmentTag: String)
}