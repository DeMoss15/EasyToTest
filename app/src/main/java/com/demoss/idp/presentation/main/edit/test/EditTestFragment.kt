package com.demoss.idp.presentation.main.edit.test

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.demoss.idp.R
import com.demoss.idp.base.BaseFragment
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.presentation.adapter.QuestionsRecyclerViewAdapter
import com.demoss.idp.util.ExtraConstants
import com.demoss.idp.util.withArguments
import kotlinx.android.synthetic.main.fragment_questions_list.*
import org.koin.android.ext.android.inject

class EditTestFragment : BaseFragment<EditTestContract.Presenter>(), EditTestContract.View {

    companion object {
        const val TAG = "com.demoss.diploma.question_list_fragment"
        fun newInstance(testId: Int): EditTestFragment = EditTestFragment()
                .withArguments(ExtraConstants.EXTRA_TEST_ID to testId)
    }

    override val presenter by inject<EditTestContract.Presenter>()
    override val layoutResourceId = R.layout.fragment_questions_list
    private val rvAdapter = QuestionsRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            presenter.init(it.getInt(ExtraConstants.EXTRA_TEST_ID))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvQuestions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
    }

    override fun showTest(test: TestModel) {
        rvAdapter.dispatchData(test.questions)
    }
}
