package com.demoss.idp.presentation.adapter

import android.view.View
import com.demoss.idp.R
import com.demoss.idp.base.BaseRecyclerViewAdapter
import com.demoss.idp.domain.model.AnswerModel
import kotlinx.android.synthetic.main.item_answer.view.*

class AnswersRecyclerViewAdapter(private val onItemClickListener: (AnswerModel) -> Unit) : BaseRecyclerViewAdapter<AnswerModel, AnswersRecyclerViewAdapter.VH, AnswersDiffUtilCallback>() {
    override val viewHolderFactory: (view: View) -> VH = { view -> VH(view) }
    override val layoutResId: Int = R.layout.item_answer
    override val diffUtilCallbackFactory: (oldList: List<AnswerModel>, newList: List<AnswerModel>) -> AnswersDiffUtilCallback =
            { oldList, newList -> AnswersDiffUtilCallback(oldList, newList) }

    inner class VH(view: View) : BaseRecyclerViewAdapter.BaseViewHolder<AnswerModel>(view) {
        override fun bindData(item: AnswerModel) {
            view.tvAnswer.text = item.text
            view.setOnClickListener { onItemClickListener(item) }
        }
    }
}