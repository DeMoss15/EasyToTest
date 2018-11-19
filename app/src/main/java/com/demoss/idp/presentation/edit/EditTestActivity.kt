package com.demoss.idp.presentation.edit

import android.os.Bundle
import com.demoss.idp.R
import com.demoss.idp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit.*
import org.koin.android.ext.android.inject

class EditTestActivity : BaseActivity<EditTestContract.Presenter>() {

    override val presenter by inject<EditTestContract.Presenter>()
    override val layoutResourceId = R.layout.activity_edit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        container
    }
}
