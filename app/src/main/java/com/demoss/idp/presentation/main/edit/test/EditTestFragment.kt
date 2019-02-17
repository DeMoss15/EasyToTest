package com.demoss.idp.presentation.main.edit.test

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.demoss.idp.R
import com.demoss.idp.base.BaseFragment
import com.demoss.idp.domain.model.EntityStatus
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.presentation.adapter.QuestionsRecyclerViewAdapter
import com.demoss.idp.presentation.main.main.MainCallback
import com.demoss.idp.util.*
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_edit_test.*
import org.koin.android.ext.android.inject

class EditTestFragment : BaseFragment<EditTestContract.Presenter>(), EditTestContract.View {

    companion object {
        const val TAG = "com.demoss.diploma.edit_test_fragment"
        fun newInstance(testId: Int): EditTestFragment = EditTestFragment()
            .withArguments(ExtraConstants.EXTRA_TEST_ID to testId)
    }

    override val presenter by inject<EditTestContract.Presenter>()
    override val layoutResourceId = R.layout.fragment_edit_test
    private lateinit var mainCallback: MainCallback
    private val rvAdapter = QuestionsRecyclerViewAdapter { question ->
        mainCallback.nextFragment(TAG, question.id)
    }

    // Lifecycle =======================================================================================================
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let { presenter.testId = it.getInt(ExtraConstants.EXTRA_TEST_ID) }
        mainCallback = activity as MainCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainCallback.readyToSetupAppBar()
        rvQuestions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            setupSwipeToDelete(rvAdapter, ::onQuestionDelete)
        }
        cbExamMode.setOnCheckedChangeListener { _, isChecked -> setSessionVisibility(isChecked) }
        sbQuestionsAmount.setOnProgressChangeListener { tvQuestionsAmount.text = it.toString() }
    }

    // View ============================================================================================================
    override fun showTest(test: TestModel) {
        if (etTestName.text.isEmpty()) etTestName.setText(test.name)
        if (test.questions.isEmpty()) {
            tvEmptyState.text = getString(
                R.string.rv_empty_data,
                resources.getQuantityString(R.plurals.question_plural, Int.MAX_VALUE)
            )
        } else {
            rvAdapter.dispatchData(test.questions.filter { it.status != EntityStatus.DROPPED })
        }
        cbExamMode.isChecked = test.examMode
        if (etPassword.text.isEmpty()) etPassword.setText(test.password)
        if (test.timer != 0L && etTimer.text.isEmpty()) etTimer.setText(test.timer.toString())
        sbQuestionsAmount.apply {
            max = test.questions.size
            progress = test.questionsAmount
        }
    }

    override fun navigateBack() {
        mainCallback.back(TAG)
    }

    // MainFragment ====================================================================================================
    override fun onFabPressed() {
        mainCallback.nextFragment(TAG, Constants.NEW_ENTITY_ID)
    }

    override fun onMenuItemPressed(itemId: Int) {
        if (activity == null) return
        when (itemId) {
            R.id.item_back -> presenter.cancel()
            R.id.item_done -> presenter.saveTest(
                etTestName.text.toString(),
                cbExamMode.isChecked,
                etTimer.text.toString().takeIf { it.isNotEmpty() }?.toLong(),
                etPassword.text.toString(),
                sbQuestionsAmount.progress
            )
            R.id.item_drop -> presenter.deleteTest()
        }
    }

    override fun showValidationError() = showToast("Test field is empty or session isn't specified for exam mode")

    override fun setupAppBar(bottomAppBar: BottomAppBar, fab: FloatingActionButton) {
        fab.setImageResource(R.drawable.ic_add)
        bottomAppBar.apply {
            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            replaceMenu(R.menu.bottomappbar_menu_edit_test)
        }
    }

    private fun setSessionVisibility(isVisible: Boolean) {
        with(if (isVisible) View.VISIBLE else View.GONE) {
            tvQuestionsAmountHint.visibility = this
            sbQuestionsAmount.visibility = this
            etTimer.visibility = this
        }
    }

    private fun onQuestionDelete(question: QuestionModel) {
        sbQuestionsAmount.apply { max -= 1 }
        presenter.deleteQuestion(question)
    }
}
