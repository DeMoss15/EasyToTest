package com.demoss.edqa.presentation.main.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.demoss.edqa.R
import com.demoss.edqa.base.BaseActivity
import com.demoss.edqa.domain.model.TestModel
import com.demoss.edqa.presentation.exam.exam.ExamActivity
import com.demoss.edqa.presentation.main.edit.answer.EditAnswerFragment
import com.demoss.edqa.presentation.main.edit.question.EditQuestionFragment
import com.demoss.edqa.presentation.main.edit.test.EditTestFragment
import com.demoss.edqa.presentation.main.tests.TestsFragment
import com.demoss.edqa.util.transaction
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View, TestsFragment.Callback {

    override val presenter by inject<MainContract.Presenter>()
    override val layoutResourceId = R.layout.activity_main

    // Lifecycle =======================================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(bottomAppBar)
        fab.setOnClickListener { applyActionForVisibleFragment { onFabPressed() } }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = (item != null).also {
        item?.let { applyActionForVisibleFragment { onMenuItemPressed(it.itemId) } }
    }

    override fun onBackPressed() = super.onBackPressed().also {
        getVisibleFragment().let { fragment -> if (fragment == null) finish() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = super.onCreateOptionsMenu(menu).also { navigateToTests() }

    // MainCallback ====================================================================================================
    override fun nextFragment(currentFragmentTag: String, entityId: Int): Unit = when (currentFragmentTag) {
        TestsFragment.TAG -> navigateToEditTest(entityId)
        EditTestFragment.TAG -> navigateToEditQuestion(entityId)
        EditQuestionFragment.TAG -> navigateToEditAnswer(entityId)
        else -> Unit
    }

    override fun back(currentFragmentTag: String) {
        supportFragmentManager.apply {
            when (currentFragmentTag) {
                TestsFragment.TAG -> finish()
                EditTestFragment.TAG -> popBackStack()
                EditQuestionFragment.TAG -> popBackStack()
                EditAnswerFragment.TAG -> popBackStack()
            }
            findFragmentByTag(currentFragmentTag)?.let { fragment -> transaction { remove(fragment) } }
        }
    }

    override fun readyToSetupAppBar(): Unit = applyActionForVisibleFragment { setupAppBar(bottomAppBar, fab) }

    // TestsFragment.Callback ==========================================================================================
    override fun startTest(test: TestModel): Unit =
        startActivity(ExamActivity.getIntent(this, test.id, test.metaData.examMode && !test.sessionResults.isEmpty()))

    // Private =========================================================================================================
    private fun navigateToTests(): Unit =
        navigateToFragment(TestsFragment.TAG) { TestsFragment.newInstance() }

    private fun navigateToEditTest(testId: Int): Unit =
        navigateToFragment(EditQuestionFragment.TAG) { EditTestFragment.newInstance(testId) }

    private fun navigateToEditQuestion(questionId: Int): Unit =
        navigateToFragment(EditQuestionFragment.TAG) { EditQuestionFragment.newInstance(questionId) }

    private fun navigateToEditAnswer(answerId: Int): Unit =
        navigateToFragment(EditAnswerFragment.TAG) { EditAnswerFragment.newInstance(answerId) }

    private fun navigateToFragment(tag: String, fragmentFabric: () -> Fragment): Unit =
        replaceFragment(supportFragmentManager.findFragmentByTag(tag) ?: fragmentFabric(), tag)

    private fun replaceFragment(fragment: Fragment, tag: String): Unit = supportFragmentManager.transaction {
        replace(container.id, fragment)
        addToBackStack(tag)
    }

    private fun applyActionForVisibleFragment(action: MainFragment.() -> Unit): Unit =
        getVisibleFragment()?.let { fragment -> (fragment as MainFragment).action() } ?: Unit

    private fun getVisibleFragment(): Fragment? = supportFragmentManager.fragments
        .firstOrNull { fragment -> fragment.isVisible }
}
