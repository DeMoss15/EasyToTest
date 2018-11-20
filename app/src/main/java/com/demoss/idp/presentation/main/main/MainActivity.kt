package com.demoss.idp.presentation.main.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.demoss.idp.R
import com.demoss.idp.base.BaseActivity
import com.demoss.idp.presentation.main.MainRouter
import com.demoss.idp.presentation.main.edit.test.EditTestFragment
import com.demoss.idp.presentation.main.tests.TestsFragment
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<MainContract.Presenter>(), MainRouter {

    override val presenter by inject<MainContract.Presenter>()
    override val layoutResourceId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(bottomAppBar)
        navigateToTests()
        fab.setOnClickListener { navigateToEditTest(0) }
    }

    override fun navigateToTests() {
        bottomAppBar.apply {
            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
        }
        supportFragmentManager.beginTransaction()
                .replace(container.id, addOrCreateFragment(TestsFragment.TAG) { TestsFragment.newInstance() })
                .commitNow()
    }

    override fun navigateToEditTest(testId: Int) {
        bottomAppBar.apply {
            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
        }
        supportFragmentManager.beginTransaction()
                .replace(container.id, addOrCreateFragment(EditTestFragment.TAG)
                { EditTestFragment.newInstance(testId) })
                .commitNow()
    }

    private fun addOrCreateFragment(tab: String, factory: () -> Fragment): Fragment =
            with(supportFragmentManager.findFragmentByTag(tab)) {
                this ?: factory()/*with(factory()) {
                    supportFragmentManager.beginTransaction()
                            .add(R.id.container, this, tab)
                            .detach(this).commitNow()
                    this
                }*/
            }
}
