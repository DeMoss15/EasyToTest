package com.demoss.edqa.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.AsyncListDiffer
import com.demoss.edqa.R
import com.demoss.edqa.base.BaseRecyclerViewAdapter
import com.demoss.edqa.domain.model.AnswerModel
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

    class DiffUtilAnswerModelItemCallback : BaseRecyclerViewAdapter.BaseDiffUtilItemCallback<AnswerModel>()
}