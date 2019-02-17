package com.demoss.idp.presentation.exam.results

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import com.demoss.idp.R
import com.demoss.idp.base.BaseFragment
import com.demoss.idp.presentation.exam.ExamCallback
import kotlinx.android.synthetic.main.fragment_results.*
import org.koin.android.ext.android.inject

class ResultsFragment : BaseFragment<ResultsContract.Presenter>(), ResultsContract.View {

    companion object {
        const val TAG = "com.demoss.idp.presentation.exam.results.fragment_results"
        fun getInstance(): ResultsFragment = ResultsFragment()
    }

    override val presenter: ResultsContract.Presenter by inject()
    override val layoutResourceId: Int = R.layout.fragment_results
    private lateinit var examCallback: ExamCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        examCallback = activity as ExamCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTestName.movementMethod = ScrollingMovementMethod()
        btnBack.setOnClickListener { examCallback.back(TAG) }
    }

    // View ========================================================================================
    override fun showTestName(testName: String) {
        tvTestName.text = testName
    }

    override fun showSpentTime(time: String) {
        tvTime.text = time
    }

    override fun showAnswersStats(stats: String) {
        tvAnswersStats.text = stats
    }
}
