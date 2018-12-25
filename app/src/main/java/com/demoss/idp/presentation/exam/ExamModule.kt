package com.demoss.idp.presentation.exam

import com.demoss.idp.presentation.exam.exam.ExamContract
import com.demoss.idp.presentation.exam.exam.ExamPresenter
import com.demoss.idp.presentation.exam.results.ResultsContract
import com.demoss.idp.presentation.exam.results.ResultsPresenter
import com.demoss.idp.presentation.exam.session.SessionContract
import com.demoss.idp.presentation.exam.session.SessionPresenter
import com.demoss.idp.presentation.exam.setup.SetupSessionContract
import com.demoss.idp.presentation.exam.setup.SetupSessionPresenter
import org.koin.dsl.module.module

val examModule = module {
    factory { ExamPresenter(get()) as ExamContract.Presenter }
    factory { SetupSessionPresenter(get()) as SetupSessionContract.Presenter }
    factory { SessionPresenter(get()) as SessionContract.Presenter }
    factory { ResultsPresenter(get()) as ResultsContract.Presenter }
}