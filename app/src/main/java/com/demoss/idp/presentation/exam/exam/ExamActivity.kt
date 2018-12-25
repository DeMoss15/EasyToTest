package com.demoss.idp.presentation.exam.exam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.demoss.idp.R
import com.demoss.idp.base.BaseActivity
import com.demoss.idp.presentation.exam.ExamCallback
import com.demoss.idp.presentation.exam.results.ResultsFragment
import com.demoss.idp.presentation.exam.session.SessionFragment
import com.demoss.idp.presentation.exam.setup.SetupSessionFragment
import com.demoss.idp.util.ExtraConstants
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class ExamActivity : BaseActivity<ExamContract.Presenter>(), ExamContract.View, ExamCallback {

    companion object {
        fun getIntent(context: Context, testId: Int): Intent = Intent(context, ExamActivity::class.java)
                .putExtra(ExtraConstants.EXTRA_TEST_ID, testId)
    }

    override val presenter: ExamContract.Presenter by inject()
    override val layoutResourceId: Int = R.layout.activity_exam

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.setTestId(intent.getIntExtra(ExtraConstants.EXTRA_TEST_ID, 0))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    // View ========================================================================================
    override fun showSettings() = navigateToSetup()

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

    private fun navigateToFragment(tag: String, fabric: ()-> Fragment) {
        replaceFragment(supportFragmentManager.findFragmentByTag(tag) ?: fabric(), tag)
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
                .replace(container.id, fragment)
                .addToBackStack(tag)
                .commit()
    }
}
