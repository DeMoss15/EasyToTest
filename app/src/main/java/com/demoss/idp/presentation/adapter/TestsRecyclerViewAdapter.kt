package com.demoss.idp.presentation.adapter

import android.view.View
import com.demoss.idp.R
import com.demoss.idp.base.BaseRecyclerViewAdapter
import com.demoss.idp.domain.model.TestModel
import kotlinx.android.synthetic.main.item_test.view.*

class TestsRecyclerViewAdapter(val onItemClick: (TestModel) -> Unit) :
    BaseRecyclerViewAdapter<TestModel, TestsRecyclerViewAdapter.ViewHolder, TestsDiffUtilCallback>() {

    override val viewHolderFactory = { v: View -> ViewHolder(v) }
    override val layoutResId = R.layout.item_test
    override val diffUtilCallbackFactory =
        { old: List<TestModel>, new: List<TestModel> -> TestsDiffUtilCallback(old, new) }

    inner class ViewHolder(view: View) : BaseRecyclerViewAdapter.BaseViewHolder<TestModel>(view) {
        override fun bindData(item: TestModel) {
            view.tvName.text = item.name
            view.setOnClickListener { onItemClick(item) }
        }
    }
}