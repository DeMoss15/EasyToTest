package com.demoss.edqa.presentation.exam.session

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.demoss.edqa.R
import com.demoss.edqa.base.BaseFragment
import com.demoss.edqa.domain.model.QuestionModel
import com.demoss.edqa.presentation.adapter.SelectableAnswersRecyclerViewAdapter
import com.demoss.edqa.presentation.exam.ExamCallback
import kotlinx.android.synthetic.main.fragment_session.*
import org.koin.android.ext.android.inject

class SessionFragment : BaseFragment<SessionContract.Presenter>(), SessionContract.View {

    companion object {
        const val TAG = "com.demoss.edqa.presentation.exam.setup.session_fragment"
        fun getInstance(): SessionFragment = SessionFragment()
    }

    override val presenter: SessionContract.Presenter by inject()
    override val layoutResourceId: Int = R.layout.fragment_session
    private lateinit var examCallback: ExamCallback
    private val rvAdapter = SelectableAnswersRecyclerViewAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        examCallback = activity as ExamCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvQuestion.movementMethod = ScrollingMovementMethod()
        rvAnswers.apply {
            val manager = LinearLayoutManager(context)
            manager.stackFromEnd = true
            layoutManager = manager
            adapter = rvAdapter
        }
        btnNext.setOnClickListener {
            if (rvAdapter.isRightAnswerShown)
                presenter.setAnswer(rvAdapter.getSelectedItem())
            else {
                rvAdapter.showRightAnswer()
                scrollToFirstRightAnswer()
            }
        }
        btnStop.setOnClickListener {
            presenter.stopSession()
        }
        presenter.startSession()
    }

    override fun showTimer(time: String) {
        tvTimer.text = time
    }

    override fun showQuestion(question: QuestionModel) {
        tvQuestion.text = question.text
        tvQuestion.scrollTo(0, 0)
        rvAdapter.isRightAnswerShown = false
        rvAdapter.dispatchData(question.answers)
    }

    override fun navigateToResults() {
        examCallback.nextFragment(TAG)
    }

    override fun showCounter(counter: String) {
        tvCounter.text = counter
    }

    private fun scrollToFirstRightAnswer() {
        rvAdapter.differ.currentList.apply {
            firstOrNull { answer -> answer.isRightAnswer }.let { firstRightAnswer ->
                rvAnswers.scrollToPosition(if (firstRightAnswer != null) indexOf(firstRightAnswer) else 0)
            }
        }
    }
}
