package com.demoss.idp.presentation.local

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.demoss.idp.R
import com.demoss.idp.base.BaseActivity
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.presentation.adapter.TestsRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_local_data.*
import org.koin.android.ext.android.inject

class LocalDataActivity : BaseActivity<LocalDataContract.Presenter>() {

    override val presenter by inject<LocalDataContract.Presenter>()
    override val layoutResourceId = R.layout.activity_local_data
    private val rvAdapter = TestsRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvTests.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

        val data = mutableListOf<TestModel>()
        for (i in 0..15){
            data.add(TestModel("test $i", listOf()))
        }
        rvAdapter.addData(data)
    }
}
