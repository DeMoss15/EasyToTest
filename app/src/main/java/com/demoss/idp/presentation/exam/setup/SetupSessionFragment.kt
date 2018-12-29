package com.demoss.idp.presentation.exam.setup

import android.content.Context
import android.os.Bundle
import android.view.View
import com.demoss.idp.R
import com.demoss.idp.base.BaseFragment
import com.demoss.idp.presentation.exam.ExamCallback
import com.demoss.idp.util.Constants
import com.demoss.idp.util.setOnProgressChangeListener
import kotlinx.android.synthetic.main.fragment_setup_session.*
import org.koin.android.ext.android.inject

class SetupSessionFragment : BaseFragment<SetupSessionContract.Presenter>(), SetupSessionContract.View {

    companion object {
        const val TAG = "com.demoss.idp.presentation.exam.setup.setup_session_fragment"
        fun getInstance(): SetupSessionFragment = SetupSessionFragment()
    }

    override val presenter: SetupSessionContract.Presenter by inject()
    override val layoutResourceId: Int = R.layout.fragment_setup_session
    private lateinit var examCallback: ExamCallback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        examCallback = activity as ExamCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(presenter.getTest()) {
            tvResultsTitle.text = name
            sbQuestionsAmount.max = questions.size
            tvQuestionsAmount.text = questions.size.toString()
        }

        sbQuestionsAmount.apply {
            progress = max
            setOnProgressChangeListener { progress ->
                tvQuestionsAmount.text = progress.toString()
            }
        }

        switchTimer.setOnCheckedChangeListener { compoundButton, checked ->
            etTimer.visibility = if (checked) View.VISIBLE else View.GONE
        }

        btnNext.setOnClickListener {
            presenter.setupSession(
                switchShuffle.isChecked,
                if (switchTimer.isChecked) etTimer.text.toString() else Constants.EMPTY_TIMER,
                sbQuestionsAmount.progress
            )
        }

        btnBack.setOnClickListener { examCallback.back(TAG) }
    }

    override fun navigateToSession() {
        examCallback.nextFragment(TAG)
    }

    override fun showTimerValidationError() {
        showToast("enter time you need between 1 and 999")
    }

    override fun showQuestionsAmountValidationError() {
        showToast("0 questions is too easy for you")
    }
}
