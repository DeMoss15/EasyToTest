package com.demoss.edqa.presentation.exam.exam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.demoss.edqa.R
import com.demoss.edqa.base.BaseActivity
import com.demoss.edqa.presentation.exam.ExamCallback
import com.demoss.edqa.presentation.exam.results.ResultsFragment
import com.demoss.edqa.presentation.exam.session.SessionFragment
import com.demoss.edqa.presentation.exam.setup.SetupSessionFragment
import com.demoss.edqa.util.ExtraConstants
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class ExamActivity : BaseActivity<ExamContract.Presenter>(), ExamContract.View, ExamCallback {

    companion object {
        fun getIntent(context: Context, testId: Int, isResultsFrame: Boolean): Intent =
            Intent(context, ExamActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra(ExtraConstants.EXTRA_TEST_ID, testId)
                .putExtra(ExtraConstants.EXTRA_RESULTS, isResultsFrame)
    }

    override val presenter: ExamContract.Presenter by inject()
    override val layoutResourceId: Int = R.layout.activity_exam

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.setExtra(
            intent.getIntExtra(ExtraConstants.EXTRA_TEST_ID, 0),
            intent.getBooleanExtra(ExtraConstants.EXTRA_RESULTS, false)
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    // View ========================================================================================
    override fun showSettings() = navigateToSetup()

    override fun showResults() = navigateToResults()

    // Callback ====================================================================================
    override fun nextFragment(currentFragmentTag: String) {
        when (currentFragmentTag) {
            SetupSessionFragment.TAG -> navigateToSession()
            SessionFragment.TAG -> navigateToResults()
        }
    }

    override fun back(currentFragmentTag: String) {
        finish()
    }

    // Private =====================================================================================
    private fun navigateToSetup() {
        navigateToFragment(SetupSessionFragment.TAG) { SetupSessionFragment.getInstance() }
    }

    private fun navigateToSession() {
        navigateToFragment(SessionFragment.TAG) { SessionFragment.getInstance() }
    }

    private fun navigateToResults() {
        navigateToFragment(ResultsFragment.TAG) { ResultsFragment.getInstance() }
    }

    private fun navigateToFragment(tag: String, fabric: () -> Fragment) {
        replaceFragment(supportFragmentManager.findFragmentByTag(tag) ?: fabric(), tag)
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(container.id, fragment)
            .addToBackStack(tag)
            .commit()
    }
}
