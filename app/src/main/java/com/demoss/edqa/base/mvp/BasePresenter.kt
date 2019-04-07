package com.demoss.edqa.base.mvp

interface BasePresenter {

    fun attachView(view: BaseView)
    fun detachView()
    fun viewShown()
    fun viewHidden()
}