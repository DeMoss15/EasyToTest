package com.demoss.idp.presentation.exam.setup

import android.content.Context
import android.os.Bundle
import android.view.View
import com.demoss.idp.R
import com.demoss.idp.base.BaseFragment
import com.demoss.idp.presentation.exam.ExamCallback
import com.demoss.idp.util.setOnProgressChangeListener
import kotlinx.android.synthetic.main.fragment_setup_session.*
import org.koin.android.ext.android.inject

class SetupSessionFragment : BaseFragment<SetupSessionContract.Presenter>() {

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
        }

        sbQuestionsAmount.setOnProgressChangeListener { progress ->
            tvQuestionsAmount.text = progress.toString()
        }

        switchTimer.setOnCheckedChangeListener { compoundButton, checked ->
            tiTimer.visibility = if (checked) View.VISIBLE else View.GONE
        }

        btnNext.setOnClickListener {
            if (sbQuestionsAmount.progress == 0) {
                showToast("too easy for you!")
                return@setOnClickListener
            }
            presenter.setupSession(
                switchShuffle.isChecked,
                if (switchTimer.isChecked) etTimer.text.toString().toLong() else 0L,
                sbQuestionsAmount.progress
            )
            examCallback.nextFragment(TAG)
        }

        btnBack.setOnClickListener { examCallback.back(TAG) }
    }
}
