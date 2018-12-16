package com.demoss.idp.presentation.main.edit.question

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.demoss.idp.R
import com.demoss.idp.base.BaseFragment
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.presentation.adapter.AnswersRecyclerViewAdapter
import com.demoss.idp.util.ExtraConstants
import com.demoss.idp.util.withArguments
import kotlinx.android.synthetic.main.fragment_edit_question.*
import org.koin.android.ext.android.inject

class EditQuestionFragment : BaseFragment<EditQuestionContract.Presenter>(), EditQuestionContract.View {

    companion object {
        const val TAG = "com.demoss.diploma.edit_question_fragment"
        fun newInstance(questionId: Int): EditQuestionFragment = EditQuestionFragment()
            .withArguments(ExtraConstants.EXTRA_QUESTION_ID to questionId)
    }

    override val presenter by inject<EditQuestionContract.Presenter>()
    override val layoutResourceId = R.layout.fragment_edit_question
    private val rvAdapter = AnswersRecyclerViewAdapter()

    // Lifecycle =======================================================================================================
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { presenter.getQuestion(it.getInt(ExtraConstants.EXTRA_QUESTION_ID)) }
        rvAnswers.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    // View ============================================================================================================
    override fun showQuestion(question: QuestionModel) {
        etQuestion.setText(question.text)
        rvAdapter.dispatchData(question.answers)
    }
}
