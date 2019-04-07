package com.demoss.edqa.presentation.exam

import com.demoss.edqa.presentation.exam.exam.ExamContract
import com.demoss.edqa.presentation.exam.exam.ExamPresenter
import com.demoss.edqa.presentation.exam.results.ResultsContract
import com.demoss.edqa.presentation.exam.results.ResultsPresenter
import com.demoss.edqa.presentation.exam.session.SessionContract
import com.demoss.edqa.presentation.exam.session.SessionPresenter
import com.demoss.edqa.presentation.exam.setup.SetupSessionContract
import com.demoss.edqa.presentation.exam.setup.SetupSessionPresenter
import org.koin.dsl.module.module

val examModule = module {
    factory { ExamPresenter(get()) as ExamContract.Presenter }
    factory { SetupSessionPresenter(get()) as SetupSessionContract.Presenter }
    factory { SessionPresenter(get()) as SessionContract.Presenter }
    factory { ResultsPresenter(get()) as ResultsContract.Presenter }
}