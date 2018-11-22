package com.demoss.idp.presentation.main.edit.question

import com.demoss.idp.R
import com.demoss.idp.base.BaseFragment
import org.koin.android.ext.android.inject

class EditQuestionFragment : BaseFragment<EditQuestionContract.Presenter>() {

    override val presenter by inject<EditQuestionContract.Presenter>()
    override val layoutResourceId = R.layout.fragment_edit_question
}
