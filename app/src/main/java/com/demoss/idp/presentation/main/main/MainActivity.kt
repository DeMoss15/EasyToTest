package com.demoss.idp.presentation.main.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.demoss.idp.R
import com.demoss.idp.base.BaseActivity
import com.demoss.idp.presentation.main.MainRouter
import com.demoss.idp.presentation.main.edit.test.EditTestFragment
import com.demoss.idp.presentation.main.tests.TestsFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<MainContract.Presenter>(), MainRouter {

    override val presenter by inject<MainContract.Presenter>()
    override val layoutResourceId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(bottomAppBar)
        navigateToTests()
        var i = true
        fab.setOnClickListener {
            if (i) navigateToEditTest(0)
            else navigateToTests()
            i = !i
        }
    }

    override fun navigateToTests() {
        bottomAppBar.apply {
            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            replaceMenu(R.menu.bottomappbar_menu_tests)
        }
        supportFragmentManager.beginTransaction()
                .replace(container.id, supportFragmentManager.findFragmentByTag(TestsFragment.TAG)
                        ?: TestsFragment.newInstance())
                .commitNow()
    }

    override fun navigateToEditTest(testId: Int) {
        bottomAppBar.apply {
            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            replaceMenu(R.menu.bottomappbar_menu_edit_test)
        }
        supportFragmentManager.beginTransaction()
                .replace(container.id, supportFragmentManager.findFragmentByTag(EditTestFragment.TAG)
                        ?: EditTestFragment.newInstance(testId))
                .commitNow()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.item_back -> {
                Snackbar.make(findViewById<View>(android.R.id.content), "back pressed", Snackbar.LENGTH_LONG).show()
            }
            R.id.item_done -> {
                Snackbar.make(findViewById<View>(android.R.id.content), "done pressed", Snackbar.LENGTH_LONG).show()
            }
            R.id.item_drop -> {
                Snackbar.make(findViewById<View>(android.R.id.content), "drop pressed", Snackbar.LENGTH_LONG).show()
            }
        }
        return true
    }
}
