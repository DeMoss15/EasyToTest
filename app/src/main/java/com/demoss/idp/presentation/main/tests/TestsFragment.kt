package com.demoss.idp.presentation.main.tests

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demoss.idp.R
import com.demoss.idp.base.BaseFragment
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.presentation.adapter.TestsRecyclerViewAdapter
import com.demoss.idp.util.pagination.Paginator
import com.demoss.idp.util.pagination.setOnNextPageListener
import kotlinx.android.synthetic.main.activity_local_data.*
import org.koin.android.ext.android.inject

class TestsFragment : BaseFragment<TestsContract.Presenter>(), Paginator.ViewController<TestModel> {

    companion object {
        const val TAG = "com.demoss.diploma.tests_fragment"
        fun newInstance(): TestsFragment = TestsFragment()
    }

    override val presenter by inject<TestsContract.Presenter>()
    override val layoutResourceId = R.layout.activity_local_data
    private val rvAdapter = TestsRecyclerViewAdapter() { test ->
        // TODO: 20.11.18 redirect to edit test fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvTests.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            setOnNextPageListener(RecyclerView.FOCUS_DOWN) {
                presenter.loadMore()
            }
        }
    }

    override fun showEmptyProgress(show: Boolean) {
        emptyProgressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showEmptyError(show: Boolean, error: Throwable?) {
        emptyState.text = "Empty data"
        emptyState.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showEmptyView(show: Boolean) {
        emptyState.text = "Empty state"
        emptyState.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showData(show: Boolean, data: List<TestModel>) {
        rvAdapter.dispatchData(data)
    }

    override fun showErrorMessage(error: Throwable) {
        emptyState.text = error.localizedMessage
        emptyState.visibility = View.VISIBLE
    }

    override fun showRefreshProgress(show: Boolean) {
        emptyProgressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showPageProgress(show: Boolean) {
        pageProgress.visibility = if (show) View.VISIBLE else View.GONE
    }
}
