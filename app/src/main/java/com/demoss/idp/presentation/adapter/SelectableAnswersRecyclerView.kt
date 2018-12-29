package com.demoss.idp.presentation.adapter

import android.view.View
import androidx.core.content.ContextCompat
import com.demoss.idp.R
import com.demoss.idp.base.BaseRecyclerViewAdapter
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.usecase.TempEntitiesFabric
import kotlinx.android.synthetic.main.item_answer.view.*

class SelectableAnswersRecyclerView :
    BaseRecyclerViewAdapter<AnswerModel, SelectableAnswersRecyclerView.VH, AnswersDiffUtilCallback>() {

    override val viewHolderFactory: (view: View) -> VH = { VH(it) }
    override val layoutResId: Int = R.layout.item_answer
    override val diffUtilCallbackFactory = { oldList: List<AnswerModel>, newList: List<AnswerModel> ->
        AnswersDiffUtilCallback(oldList, newList)
    }

    private var selectedItem: AnswerModel? = null
    var isRightAnswerShown = false

    fun getSelectedItem(): AnswerModel = selectedItem
        ?: TempEntitiesFabric.createTempAnswer().apply { isRightAnswer = false }

    fun showRightAnswer() {
        if (!isRightAnswerShown) {
            isRightAnswerShown = true
            data.filter { answer -> answer.isRightAnswer }.forEach { answer ->
                redrawItem(answer)
            }
        }
    }

    private fun redrawItem(item: AnswerModel) {
        notifyItemChanged(data.indexOf(item))
    }

    inner class VH(view: View) : BaseRecyclerViewAdapter.BaseViewHolder<AnswerModel>(view) {

        override fun bindData(item: AnswerModel) {
            view.setOnClickListener { if (!isRightAnswerShown) changeSelection(item) }
            view.setBackgroundColor(getItemColor(item))
            view.tvAnswer.text = item.text
        }

        private fun changeSelection(item: AnswerModel) {
            val previousItem = selectedItem
            selectedItem = item
            previousItem?.let { redrawItem(it) }
            selectedItem?.let { redrawItem(it) }
        }

        private fun getItemColor(item: AnswerModel): Int = ContextCompat.getColor(
            view.context,
            getBackgroundColorResource(item)
        )

        private fun getBackgroundColorResource(item: AnswerModel): Int = when {
            isRightAnswerShown && item.isRightAnswer -> R.color.colorRightAnswer
            selectedItem == item -> R.color.colorChecked
            else -> R.color.colorUnchecked
        }
    }
}