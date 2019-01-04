package com.demoss.idp.presentation.main.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.demoss.idp.R
import com.demoss.idp.base.BaseActivity
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.presentation.exam.exam.ExamActivity
import com.demoss.idp.presentation.main.edit.answer.EditAnswerFragment
import com.demoss.idp.presentation.main.edit.question.EditQuestionFragment
import com.demoss.idp.presentation.main.edit.test.EditTestFragment
import com.demoss.idp.presentation.main.tests.TestsFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View, TestsFragment.Callback {

    override val presenter by inject<MainContract.Presenter>()
    override val layoutResourceId = R.layout.activity_main

    // Lifecycle =======================================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(bottomAppBar)
        navigateToTests()
        fab.setOnClickListener { applyActionForVisibleFragment { onFabPressed() } }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let { applyActionForVisibleFragment { onMenuItemPressed(it.itemId) } }
        return item != null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.fragments
            .firstOrNull { fragment -> fragment.isVisible }
            .let { fragment -> if (fragment == null) finish() }
    }

    // MainCallback ====================================================================================================
    override fun nextFragment(currentFragmentTag: String, entityId: Int) {
        when (currentFragmentTag) {
            TestsFragment.TAG -> navigateToEditTest(entityId)
            EditTestFragment.TAG -> navigateToEditQuestion(entityId)
            EditQuestionFragment.TAG -> navigateToEditAnswer(entityId)
        }
    }

    override fun back(currentFragmentTag: String) {
        when (currentFragmentTag) {
            TestsFragment.TAG -> finish()
            EditTestFragment.TAG -> supportFragmentManager.popBackStack()
            EditQuestionFragment.TAG -> supportFragmentManager.popBackStack()
            EditAnswerFragment.TAG -> supportFragmentManager.popBackStack()
        }
        supportFragmentManager.findFragmentByTag(currentFragmentTag)?.let {
            supportFragmentManager.beginTransaction().remove(it)
        }
    }

    override fun readyToSetupAppBar() = applyActionForVisibleFragment { setupAppBar(bottomAppBar, fab) }

    // TestsFragment.Callback ==========================================================================================
    override fun startTest(test: TestModel) {
        startActivity(ExamActivity.getIntent(this, test.id))
    }

    // Private =========================================================================================================
    private fun navigateToTests() =
        navigateToFragment(TestsFragment.TAG) { TestsFragment.newInstance() }

    private fun navigateToEditTest(testId: Int) =
        navigateToFragment(EditQuestionFragment.TAG) { EditTestFragment.newInstance(testId) }

    private fun navigateToEditQuestion(questionId: Int) =
        navigateToFragment(EditQuestionFragment.TAG) { EditQuestionFragment.newInstance(questionId) }

    private fun navigateToEditAnswer(answerId: Int) =
        navigateToFragment(EditAnswerFragment.TAG) { EditAnswerFragment.newInstance(answerId) }

    private fun navigateToFragment(tag: String, fragmentFabric: () -> Fragment): Unit =
        replaceFragment(supportFragmentManager.findFragmentByTag(tag) ?: fragmentFabric(), tag)

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(container.id, fragment)
            .addToBackStack(tag)
            .commit()
    }

    private fun applyActionForVisibleFragment(action: MainFragment.() -> Unit) {
        supportFragmentManager.fragments
            .firstOrNull { fragment -> fragment.isVisible }
            ?.let { fragment -> (fragment as MainFragment).action() }
    }
}
