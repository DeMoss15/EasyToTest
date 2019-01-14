package com.demoss.idp.presentation.main.dialog

import android.view.View
import androidx.recyclerview.widget.AsyncListDiffer
import com.demoss.idp.R
import com.demoss.idp.base.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_text.view.*

class SimpleItemsListAdapter(val onItemClickListener: (String) -> Unit)
    : BaseRecyclerViewAdapter<String, SimpleItemsListAdapter.VH>() {

    override val viewHolderFactory: (view: View) -> VH = { VH(it) }
    override val layoutResId: Int = R.layout.item_text
    override var differ: AsyncListDiffer<String> = AsyncListDiffer(this, DiffUtilStringItemCallback())

    inner class VH(view: View) : BaseRecyclerViewAdapter.BaseViewHolder<String>(view) {
        override fun bindData(item: String) {
            view.tvItemName.text = item
            view.setOnClickListener { onItemClickListener(item) }
        }
    }


    inner class DiffUtilStringItemCallback(): BaseDiffUtilItemCallback<String>() {
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
    }
}