package com.demoss.idp.presentation.main.dialog

import android.view.View
import com.demoss.idp.R
import com.demoss.idp.base.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_text.view.*

class SimpleItemsListAdapter(val onItemClickListener: (String) -> Unit)
    : BaseRecyclerViewAdapter<String, SimpleItemsListAdapter.VH, SimpleItemsListAdapter.DUC>() {

    override val viewHolderFactory: (view: View) -> VH = { VH(it) }
    override val layoutResId: Int = R.layout.item_text
    override val diffUtilCallbackFactory: (oldList: List<String>, newList: List<String>) -> DUC =
            { old, new -> DUC(old, new) }

    inner class VH(view: View) : BaseRecyclerViewAdapter.BaseViewHolder<String>(view) {
        override fun bindData(item: String) {
            view.tvItemName.text = item
            view.setOnClickListener { onItemClickListener(item) }
        }
    }

    inner class DUC(oldList: List<String>, newList: List<String>)
        : BaseRecyclerViewAdapter.BaseDiffUtilCallback<String>(oldList, newList)
}