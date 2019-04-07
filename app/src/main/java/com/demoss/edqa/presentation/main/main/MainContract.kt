package com.demoss.edqa.presentation.main.main

import com.demoss.edqa.base.mvp.BasePresenter
import com.demoss.edqa.base.mvp.BaseView

interface MainContract {

    interface Presenter : BasePresenter
    interface View : BaseView, MainCallback
}