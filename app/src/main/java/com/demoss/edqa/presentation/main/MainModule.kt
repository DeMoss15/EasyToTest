package com.demoss.edqa.presentation.main

import com.demoss.edqa.presentation.main.edit.answer.EditAnswerContract
import com.demoss.edqa.presentation.main.edit.answer.EditAnswerPresenter
import com.demoss.edqa.presentation.main.edit.question.EditQuestionContract
import com.demoss.edqa.presentation.main.edit.question.EditQuestionPresenter
import com.demoss.edqa.presentation.main.edit.test.EditTestContract
import com.demoss.edqa.presentation.main.edit.test.EditTestPresenter
import com.demoss.edqa.presentation.main.main.MainContract
import com.demoss.edqa.presentation.main.main.MainPresenter
import com.demoss.edqa.presentation.main.settings.SettingsContract
import com.demoss.edqa.presentation.main.settings.SettingsPresenter
import com.demoss.edqa.presentation.main.tests.TestsContract
import com.demoss.edqa.presentation.main.tests.TestsPresenter
import org.koin.dsl.module.module

val mainModule = module {
    factory { MainPresenter() as MainContract.Presenter }
    factory { TestsPresenter(get(), get(), get()) as TestsContract.Presenter }
    factory { EditTestPresenter(get()) as EditTestContract.Presenter }
    factory { EditQuestionPresenter(get()) as EditQuestionContract.Presenter }
    factory { EditAnswerPresenter(get()) as EditAnswerContract.Presenter }
    single { SettingsPresenter() as SettingsContract.Presenter }
}