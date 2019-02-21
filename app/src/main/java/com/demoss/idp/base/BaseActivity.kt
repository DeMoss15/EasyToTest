package com.demoss.idp.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.demoss.idp.R
import com.demoss.idp.base.mvp.BasePresenter
import com.demoss.idp.base.mvp.BaseView
import com.demoss.idp.util.Constants

abstract class BaseActivity<Presenter : BasePresenter> : AppCompatActivity(), BaseView {

    abstract val presenter: Presenter
    abstract val layoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(
            getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
                .getInt(Constants.THEME_KEY, R.style.AppTheme)
        )
        super.onCreate(savedInstanceState)
        setContentView(layoutResourceId)
        presenter.attachView(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.viewShown()
    }

    override fun onStop() {
        super.onStop()
        presenter.viewHidden()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    final override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    final override fun hideKeyboard() {
        (getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow((currentFocus ?: View(this)).windowToken, 0)
    }
}