package com.demoss.idp.presentation.exam.session

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.demoss.idp.R
import com.demoss.idp.base.BaseFragment
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.presentation.adapter.AnswersRecyclerViewAdapter
import com.demoss.idp.presentation.exam.ExamCallback
import kotlinx.android.synthetic.main.fragment_session.*
import org.koin.android.ext.android.inject

class SessionFragment : BaseFragment<SessionContract.Presenter>(), SessionContract.View {

    companion object {
        const val TAG = "com.demoss.idp.presentation.exam.setup.session_fragment"
        fun getInstance(): SessionFragment = SessionFragment()
    }

    override val presenter: SessionContract.Presenter by inject()
    override val layoutResourceId: Int = R.layout.fragment_session
    private lateinit var examCallback: ExamCallback
    private val rvAdapter = AnswersRecyclerViewAdapter { presenter.setAnswer(it) }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        examCallback = activity as ExamCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.startSession()

        rvAnswers.apply {
            val manager = LinearLayoutManager(context)
            manager.stackFromEnd = true
            layoutManager = manager
            adapter = rvAdapter
        }
    }

    override fun showTimer(time: String) {
        tvTimer.text = time
    }

    override fun showQuestion(question: QuestionModel) {
        tvQuestion.text = question.text
        rvAdapter.dispatchData(question.answers)
    }

    override fun navigateToResults() {
        examCallback.nextFragment(TAG)
    }
}
