package com.demoss.idp.presentation.adapter

import android.view.View
import com.demoss.idp.R
import com.demoss.idp.base.BaseRecyclerViewAdapter
import com.demoss.idp.domain.model.QuestionModel
import kotlinx.android.synthetic.main.item_question.view.*

class QuestionsRecyclerViewAdapter :
    BaseRecyclerViewAdapter<QuestionModel, QuestionsRecyclerViewAdapter.VH, QuestionsDiffUtilCallback>() {

    override val layoutResId = R.layout.item_question
    override val viewHolderFactory: (view: View) -> VH = { VH(it) }
    override val diffUtilCallbackFactory: (oldList: List<QuestionModel>, newList: List<QuestionModel>) -> QuestionsDiffUtilCallback =
        { old, new -> QuestionsDiffUtilCallback(old, new) }

    inner class VH(view: View) : BaseRecyclerViewAdapter.BaseViewHolder<QuestionModel>(view) {
        override fun bindData(item: QuestionModel) {
            view.tvQuestion.text = item.text
        }
    }
}