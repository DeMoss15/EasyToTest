package com.demoss.idp.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.AsyncListDiffer
import com.demoss.idp.R
import com.demoss.idp.base.BaseRecyclerViewAdapter
import com.demoss.idp.domain.model.QuestionModel
import kotlinx.android.synthetic.main.item_question.view.*

class QuestionsRecyclerViewAdapter(
    private val onItemClick: (QuestionModel) -> Unit
) : BaseRecyclerViewAdapter<QuestionModel, QuestionsRecyclerViewAdapter.VH>() {

    override val layoutResId = R.layout.item_question
    override val viewHolderFactory: (view: View) -> VH = { VH(it) }
    override var differ: AsyncListDiffer<QuestionModel> =
        AsyncListDiffer(this, DiffUtilsQuestionModelItemCallback())

    inner class VH(view: View) : BaseRecyclerViewAdapter.BaseViewHolder<QuestionModel>(view) {
        override fun bindData(item: QuestionModel) {
            view.tvQuestion.text = item.text
            view.setOnClickListener { onItemClick(item) }
        }
    }

    class DiffUtilsQuestionModelItemCallback : BaseDiffUtilItemCallback<QuestionModel>() {
        override fun areContentsTheSame(oldItem: QuestionModel, newItem: QuestionModel): Boolean =
                oldItem == newItem
    }
}