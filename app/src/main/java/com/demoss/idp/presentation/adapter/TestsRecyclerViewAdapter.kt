package com.demoss.idp.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.AsyncListDiffer
import com.demoss.idp.R
import com.demoss.idp.base.BaseRecyclerViewAdapter
import com.demoss.idp.domain.model.TestModel
import kotlinx.android.synthetic.main.item_test.view.*

class TestsRecyclerViewAdapter(
    val onItemClick: (TestModel, Action) -> Unit
) : BaseRecyclerViewAdapter<TestModel, TestsRecyclerViewAdapter.ViewHolder>() {

    override val viewHolderFactory = { v: View -> ViewHolder(v) }
    override val layoutResId = R.layout.item_test
    override var differ: AsyncListDiffer<TestModel> =
        AsyncListDiffer<TestModel>(this, DiffUtilTestModelItemCallback())

    inner class ViewHolder(view: View) : BaseRecyclerViewAdapter.BaseViewHolder<TestModel>(view) {
        override fun bindData(item: TestModel) {
            view.tvName.text = item.name
            view.setOnClickListener { onItemClick(differ.currentList.first { it.id == item.id }, Action.SELECT) }
            view.ivEdit.setOnClickListener { onItemClick(differ.currentList.first { it.id == item.id }, Action.EDIT) }
            view.ivShare.setOnClickListener { onItemClick(differ.currentList.first { it.id == item.id }, Action.SHARE) }
        }
    }

    enum class Action {
        SELECT,
        EDIT,
        SHARE
    }

    class DiffUtilTestModelItemCallback : BaseDiffUtilItemCallback<TestModel>()
}