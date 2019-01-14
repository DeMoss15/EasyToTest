package com.demoss.idp.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.AsyncListDiffer
import com.demoss.idp.R
import com.demoss.idp.base.BaseRecyclerViewAdapter
import com.demoss.idp.domain.model.AnswerModel
import kotlinx.android.synthetic.main.item_answer.view.*

class AnswersRecyclerViewAdapter(private val onItemClickListener: (AnswerModel) -> Unit) : BaseRecyclerViewAdapter<AnswerModel, AnswersRecyclerViewAdapter.VH>() {
    override val viewHolderFactory: (view: View) -> VH = { view -> VH(view) }
    override val layoutResId: Int = R.layout.item_answer
    override var differ: AsyncListDiffer<AnswerModel> = AsyncListDiffer(this, DiffUtilAnswerModelItemCallback())

    inner class VH(view: View) : BaseRecyclerViewAdapter.BaseViewHolder<AnswerModel>(view) {
        override fun bindData(item: AnswerModel) {
            view.tvAnswer.text = item.text
            view.setOnClickListener { onItemClickListener(item) }
        }
    }

    class DiffUtilAnswerModelItemCallback(): BaseRecyclerViewAdapter.BaseDiffUtilItemCallback<AnswerModel>() {
        override fun areContentsTheSame(oldItem: AnswerModel, newItem: AnswerModel): Boolean =
            oldItem == newItem
    }
}