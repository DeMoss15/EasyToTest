package com.demoss.idp.presentation.main.tests

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demoss.idp.R
import com.demoss.idp.base.BaseFragment
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.presentation.adapter.TestsRecyclerViewAdapter
import com.demoss.idp.presentation.main.dialog.SimpleItemsListDialogFragment
import com.demoss.idp.presentation.main.main.MainCallback
import com.demoss.idp.util.Constants
import com.demoss.idp.util.pagination.setOnNextPageListener
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_local_data.*
import org.koin.android.ext.android.inject
import java.io.File
import java.io.FileWriter

class TestsFragment : BaseFragment<TestsContract.Presenter>(), TestsContract.View {

    companion object {
        const val TAG = "com.demoss.diploma.tests_fragment"
        const val BROWSE_FILE_REQUEST_CODE = 1
        const val REQUEST_PERMISSION = 2
        fun newInstance(): TestsFragment = TestsFragment()
    }

    override val presenter by inject<TestsContract.Presenter>()
    override val layoutResourceId = R.layout.activity_local_data
    private lateinit var mainCallback: MainCallback
    private lateinit var callback: Callback
    private val rvAdapter = TestsRecyclerViewAdapter { test, action ->
        when (action) {
            TestsRecyclerViewAdapter.Action.SELECT -> callback.startTest(test)
            TestsRecyclerViewAdapter.Action.EDIT -> mainCallback.nextFragment(TAG, test.id)
            TestsRecyclerViewAdapter.Action.SHARE -> presenter.share(test)
        }
    }

    // Lifecycle =======================================================================================================
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainCallback = activity as MainCallback
        callback = activity as Callback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainCallback.readyToSetupAppBar()
        rvTests.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            setOnNextPageListener(RecyclerView.FOCUS_DOWN) {
                presenter.loadMore()
            }
        }
    }

    // View ============================================================================================================
    override fun share(string: String) {
        context?.let {

            val file = File(Environment.getExternalStorageDirectory().toString() + "/" + "exported_test.txt")
            val writer = FileWriter(file)
            writer.append(string)
            writer.flush()
            writer.close()

            val path = FileProvider.getUriForFile(it, "com.demoss.idp.fileprovider", file)

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, path)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                type = "plain/*"
            }
            startActivity(sendIntent)
        }
    }

    // MainFragment ====================================================================================================
    override fun onFabPressed() {
        SimpleItemsListDialogFragment.Builder().apply {
            title = "Choose way to add test" // todo extract resources
            itemsList = listOf("file", "input")
            onClickListener = { item ->
                when (item) {
                    itemsList[0] -> startActivityForResult(
                            Intent(Intent.ACTION_GET_CONTENT).setType("text/plain"),
                            BROWSE_FILE_REQUEST_CODE
                    )
                    itemsList[1] -> mainCallback.nextFragment(TAG, Constants.NEW_ENTITY_ID)
                }
            }
        }.build().show(childFragmentManager, TAG)
    }

    override fun onMenuItemPressed(itemId: Int) {
        // TODO: add menu handling
    }

    override fun setupAppBar(bottomAppBar: BottomAppBar, fab: FloatingActionButton) {
        fab.setImageResource(R.drawable.ic_add)
        bottomAppBar.apply {
            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            replaceMenu(R.menu.bottomappbar_menu_tests)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BROWSE_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            presenter.parseFileStream(
                Single.fromCallable { data?.data?.let { dd -> context?.contentResolver?.openInputStream(dd) } }
            )
        }
    }

    // Paginator =======================================================================================================
    override fun showEmptyProgress(show: Boolean) {
        emptyProgressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showEmptyError(show: Boolean, error: Throwable?) {
        emptyState.text = getText(R.string.rv_error_message)
        emptyState.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showEmptyView(show: Boolean) {
        emptyState.text =
                getString(R.string.rv_empty_data, resources.getQuantityString(R.plurals.test_plural, Int.MAX_VALUE))
        emptyState.visibility = if (show) View.VISIBLE else View.GONE
        rvTests.visibility = if (show) View.GONE else View.VISIBLE
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

    interface Callback {
        fun startTest(test: TestModel)
    }

    private fun share(test: TestModel) {
        context?.let { ctx ->
            val writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE

            if (ContextCompat.checkSelfPermission(ctx, writePermission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity as Activity, arrayOf(writePermission), REQUEST_PERMISSION)
            } else presenter.share(test)
        }
    }
}
