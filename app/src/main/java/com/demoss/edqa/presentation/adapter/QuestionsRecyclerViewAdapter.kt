package com.demoss.edqa.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.AsyncListDiffer
import com.demoss.edqa.R
import com.demoss.edqa.base.BaseRecyclerViewAdapter
import com.demoss.edqa.domain.model.QuestionModel
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

    class DiffUtilsQuestionModelItemCallback : BaseDiffUtilItemCallback<QuestionModel>()
}