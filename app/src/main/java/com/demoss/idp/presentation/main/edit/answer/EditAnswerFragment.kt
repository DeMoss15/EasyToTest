package com.demoss.idp.presentation.main.edit.answer

import android.content.Context
import android.os.Bundle
import android.view.View
import com.demoss.idp.R
import com.demoss.idp.base.BaseFragment
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.presentation.main.main.MainCallback
import com.demoss.idp.util.ExtraConstants
import com.demoss.idp.util.withArguments
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_edit_answer.*
import org.koin.android.ext.android.inject

class EditAnswerFragment : BaseFragment<EditAnswerContract.Presenter>(), EditAnswerContract.View {

    companion object {
        const val TAG = "com.demoss.diploma.edit_answer_fragment"
        fun newInstance(answerId: Int): EditAnswerFragment = EditAnswerFragment()
                .withArguments(ExtraConstants.EXTRA_ANSWER_ID to answerId)
    }

    override val presenter by inject<EditAnswerContract.Presenter>()
    override val layoutResourceId = R.layout.fragment_edit_answer
    private lateinit var mainCallback: MainCallback

    // Lifecycle ===================================================================================
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainCallback = activity as MainCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainCallback.readyToSetupAppBar()
        arguments?.getInt(ExtraConstants.EXTRA_ANSWER_ID)?.let {
            presenter.getAnswer(it)
        }
    }

    // View ========================================================================================
    override fun showAnswer(answer: AnswerModel) {
        etAnswer.setText(answer.text)
        cbIsRight.isChecked = answer.isRightAnswer
    }

    // MainFragment ================================================================================
    override fun onFabPressed() {
        presenter.saveAnswer(etAnswer.text.toString(), cbIsRight.isChecked)
        mainCallback.back(TAG)
    }

    override fun onMenuItemPressed(itemId: Int) {
        // no menu
    }

    override fun setupAppBar(bottomAppBar: BottomAppBar, fab: FloatingActionButton) {
        fab.setImageResource(R.drawable.ic_done)
        bottomAppBar.apply {
            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            replaceMenu(R.menu.bottomappbar_menu_tests)
        }
    }
}
