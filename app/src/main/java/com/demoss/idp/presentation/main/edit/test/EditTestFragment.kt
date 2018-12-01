package com.demoss.idp.presentation.main.edit.test

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.demoss.idp.R
import com.demoss.idp.base.BaseFragment
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.presentation.adapter.QuestionsRecyclerViewAdapter
import com.demoss.idp.presentation.main.main.MainCallback
import com.demoss.idp.util.ExtraConstants
import com.demoss.idp.util.withArguments
import kotlinx.android.synthetic.main.fragment_edit_test.*
import org.koin.android.ext.android.inject

class EditTestFragment : BaseFragment<EditTestContract.Presenter>(), EditTestContract.View {

    companion object {
        const val TAG = "com.demoss.diploma.question_list_fragment"
        fun newInstance(testId: Int): EditTestFragment = EditTestFragment()
            .withArguments(ExtraConstants.EXTRA_TEST_ID to testId)
    }

    override val presenter by inject<EditTestContract.Presenter>()
    override val layoutResourceId = R.layout.fragment_edit_test
    private lateinit var mainCallback: MainCallback
    private val rvAdapter = QuestionsRecyclerViewAdapter { action, question ->
        when (action) {
            QuestionsRecyclerViewAdapter.Action.SELECT -> mainCallback.nextFragment(TAG, question.id)
            QuestionsRecyclerViewAdapter.Action.DELETE -> TODO()
        }
    }

    // Lifecycle =======================================================================================================
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainCallback = activity as MainCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            presenter.init(it.getInt(ExtraConstants.EXTRA_TEST_ID))
        }
        rvQuestions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
    }

    // View ============================================================================================================
    override fun showTest(test: TestModel) {
        etTestName.setText(test.name)
        if (test.questions.isEmpty()) {
            tvEmptyState.text = getString(R.string.rv_empty_data, resources.getQuantityString(R.plurals.question_plural, 2))
        } else {
            rvAdapter.dispatchData(test.questions)
        }
    }

    override fun navigateBack() {
        mainCallback.back(TAG)
    }

    // MainFragment ====================================================================================================
    override fun onFabPressed() {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun onMenuItemPressed(itemId: Int) {
        if (activity == null) return
        when (itemId) {
            R.id.item_back -> mainCallback.back(TAG)
            R.id.item_done -> presenter.saveTest()
            R.id.item_drop -> presenter.deleteTest()
        }
    }
}
