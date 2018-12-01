package com.demoss.idp.presentation.main.main

import android.os.Bundle
import android.view.MenuItem
import com.demoss.idp.R
import com.demoss.idp.base.BaseActivity
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.presentation.main.edit.test.EditTestFragment
import com.demoss.idp.presentation.main.tests.TestsFragment
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<MainContract.Presenter>(), MainCallback, TestsFragment.Callback {

    override val presenter by inject<MainContract.Presenter>()
    override val layoutResourceId = R.layout.activity_main

    // Lifecycle =======================================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(bottomAppBar)
        navigateToTests()
        fab.setOnClickListener {
            supportFragmentManager.fragments
                .firstOrNull { fragment -> fragment.isVisible }
                ?.let { fragment -> (fragment as MainFragment).onFabPressed() }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) return false
        supportFragmentManager.fragments
            .firstOrNull { fragment -> fragment.isVisible }
            ?.let { fragment -> (fragment as MainFragment).onMenuItemPressed(item.itemId) }
        return true
    }

    // MainCallback ====================================================================================================
    override fun nextFragment(currentFragmentTag: String, entityId: Int) {
        when (currentFragmentTag) {
            TestsFragment.TAG -> { navigateToEditTest(entityId) }
            EditTestFragment.TAG -> {}
        }
    }

    override fun back(currentFragmentTag: String) {
        when (currentFragmentTag) {
            TestsFragment.TAG -> { onBackPressed() }
            EditTestFragment.TAG -> { navigateToTests() }
        }
    }

    // TestsFragment.Callback ==========================================================================================
    override fun startTest(test: TestModel) {
        // TODO : implement
    }

    // Private =========================================================================================================
    private fun navigateToTests() {
        bottomAppBar.apply {
            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            replaceMenu(R.menu.bottomappbar_menu_tests)
        }
        supportFragmentManager.beginTransaction()
            .replace(
                container.id, supportFragmentManager.findFragmentByTag(TestsFragment.TAG)
                    ?: TestsFragment.newInstance()
            )
            .commitNow()
    }

    private fun navigateToEditTest(testId: Int) {
        bottomAppBar.apply {
            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            replaceMenu(R.menu.bottomappbar_menu_edit_test)
        }
        supportFragmentManager.beginTransaction()
            .replace(
                container.id, supportFragmentManager.findFragmentByTag(EditTestFragment.TAG)
                    ?: EditTestFragment.newInstance(testId)
            )
            .commitNow()
    }
}
