package com.demoss.edqa.presentation.main.edit.question

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.demoss.edqa.R
import com.demoss.edqa.base.BaseFragment
import com.demoss.edqa.domain.model.EntityStatus
import com.demoss.edqa.domain.model.QuestionModel
import com.demoss.edqa.presentation.adapter.AnswersRecyclerViewAdapter
import com.demoss.edqa.presentation.main.main.MainCallback
import com.demoss.edqa.util.Constants
import com.demoss.edqa.util.ExtraConstants
import com.demoss.edqa.util.setupSwipeToDelete
import com.demoss.edqa.util.withArguments
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_edit_question.*
import org.koin.android.ext.android.inject

class EditQuestionFragment : BaseFragment<EditQuestionContract.Presenter>(), EditQuestionContract.View {

    companion object {
        const val TAG = "com.demoss.edqa.edit_question_fragment"
        fun newInstance(questionId: Int): EditQuestionFragment = EditQuestionFragment()
                .withArguments(ExtraConstants.EXTRA_QUESTION_ID to questionId)
    }

    override val presenter by inject<EditQuestionContract.Presenter>()
    override val layoutResourceId = R.layout.fragment_edit_question
    private lateinit var mainCallback: MainCallback
    private val rvAdapter = AnswersRecyclerViewAdapter { mainCallback.nextFragment(TAG, it.id) }

    // Lifecycle =======================================================================================================
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let { presenter.questionId = it.getInt(ExtraConstants.EXTRA_QUESTION_ID) }
        mainCallback = activity as MainCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainCallback.readyToSetupAppBar()
        rvAnswers.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context)
            setupSwipeToDelete(rvAdapter) {
                presenter.deleteAnswer(it)
            }
        }
    }

    // View ============================================================================================================
    override fun showQuestion(question: QuestionModel) {
        if (etQuestion.text.isEmpty()) etQuestion.setText(question.text)
        if (question.answers.isEmpty()) {
            tvEmptyState.text = getString(
                    R.string.rv_empty_data,
                    resources.getQuantityString(R.plurals.answer_plural, Int.MAX_VALUE)
            )
        } else {
            rvAdapter.dispatchData(question.answers.filter { it.status != EntityStatus.DROPPED })
        }
    }

    override fun navigateBack() {
        mainCallback.back(TAG)
    }

    override fun showEmptyDataError() {
        showToast(getString(R.string.empty_field_error))
    }

    // MainFragment ====================================================================================================
    override fun onFabPressed() {
        mainCallback.nextFragment(TAG, Constants.NEW_ENTITY_ID)
    }

    override fun onMenuItemPressed(itemId: Int) {
        if (activity == null) return
        when (itemId) {
            R.id.item_back -> presenter.cancel()
            R.id.item_done -> presenter.saveQuestion(etQuestion.text.toString())
            R.id.item_drop -> presenter.deleteQuestion()
        }
    }

    override fun setupAppBar(bottomAppBar: BottomAppBar, fab: FloatingActionButton) {
        fab.setImageResource(R.drawable.ic_add)
        bottomAppBar.apply {
            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            replaceMenu(R.menu.bottomappbar_menu_edit_test)
        }
    }
}
