package com.demoss.idp.presentation.main.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.demoss.idp.R
import com.demoss.idp.base.BaseActivity
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.presentation.main.edit.question.EditQuestionFragment
import com.demoss.idp.presentation.main.edit.test.EditTestFragment
import com.demoss.idp.presentation.main.tests.TestsFragment
import com.google.android.material.bottomappbar.BottomAppBar
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
        fab.setOnClickListener { applyActionForAllMainFragments { onFabPressed() } }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let { applyActionForAllMainFragments { onMenuItemPressed(it.itemId) } }
        return item != null
    }

    // MainCallback ====================================================================================================
    override fun nextFragment(currentFragmentTag: String, entityId: Int) {
        when (currentFragmentTag) {
            TestsFragment.TAG -> navigateToEditTest(entityId)
            EditTestFragment.TAG -> navigateToEditQuestion(entityId)
        }
    }

    override fun back(currentFragmentTag: String) {
        when (currentFragmentTag) {
            TestsFragment.TAG -> onBackPressed()
            EditTestFragment.TAG -> {
                setUpAppBarCommonMode()
                supportFragmentManager.popBackStack()
            }
            EditQuestionFragment.TAG -> supportFragmentManager.popBackStack()
        }
    }

    // TestsFragment.Callback ==========================================================================================
    override fun startTest(test: TestModel) {
        // TODO : implement
    }

    // Private =========================================================================================================
    private fun navigateToTests() {
        setUpAppBarCommonMode()
        navigateToFragment(TestsFragment.TAG) { TestsFragment.newInstance() }
    }

    private fun navigateToEditTest(testId: Int) {
        setUpAppBarEditMode()
        navigateToFragment(EditQuestionFragment.TAG) { EditTestFragment.newInstance(testId) }
    }

    private fun navigateToEditQuestion(questionId: Int) {
        setUpAppBarEditMode()
        navigateToFragment(EditQuestionFragment.TAG) { EditQuestionFragment.newInstance(questionId) }
    }

    private fun navigateToFragment(tag: String, fragmentFabric: () -> Fragment): Unit  =
        executeTransaction(supportFragmentManager.findFragmentByTag(tag) ?: fragmentFabric(), tag)

    private fun setUpAppBarCommonMode() {
        bottomAppBar.apply {
            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            replaceMenu(R.menu.bottomappbar_menu_tests)
        }
    }

    private fun setUpAppBarEditMode() {
        bottomAppBar.apply {
            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            replaceMenu(R.menu.bottomappbar_menu_edit_test)
        }
    }

    private fun executeTransaction(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(container.id, fragment)
            .addToBackStack(tag)
            .commit()
    }

    private fun applyActionForAllMainFragments(action: MainFragment.() -> Unit) {
        supportFragmentManager.fragments
            .firstOrNull { fragment -> fragment.isVisible }
            ?.let { fragment -> (fragment as MainFragment).action() }
    }
}
