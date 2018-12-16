package com.demoss.idp.presentation.adapter

import android.view.View
import com.demoss.idp.R
import com.demoss.idp.base.BaseRecyclerViewAdapter
import com.demoss.idp.domain.model.AnswerModel
import kotlinx.android.synthetic.main.item_edit_answer.view.*

class AnswersRecyclerViewAdapter: BaseRecyclerViewAdapter<AnswerModel, AnswersRecyclerViewAdapter.VH, AnswersDiffUtilCallback>() {
    override val viewHolderFactory: (view: View) -> VH = { view -> VH(view) }
    override val layoutResId: Int = R.layout.item_edit_answer
    override val diffUtilCallbackFactory: (oldList: List<AnswerModel>, newList: List<AnswerModel>) -> AnswersDiffUtilCallback =
        { oldList, newList ->  AnswersDiffUtilCallback(oldList, newList) }

    inner class VH(view: View): BaseRecyclerViewAdapter.BaseViewHolder<AnswerModel>(view) {
        override fun bindData(item: AnswerModel) {
            view.editText.setText(item.text)
        }
    }
}