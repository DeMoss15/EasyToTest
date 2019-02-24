package com.demoss.idp.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.AsyncListDiffer
import com.demoss.idp.base.BaseRecyclerViewAdapter
import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.usecase.TempEntitiesFabric
import kotlinx.android.synthetic.main.item_answer.view.*
import android.util.TypedValue
import android.content.Context
import androidx.annotation.AttrRes
import com.demoss.idp.R


class SelectableAnswersRecyclerViewAdapter :
    BaseRecyclerViewAdapter<AnswerModel, SelectableAnswersRecyclerViewAdapter.VH>() {

    override val viewHolderFactory: (view: View) -> VH = { VH(it) }
    override val layoutResId: Int = R.layout.item_answer
    override var differ: AsyncListDiffer<AnswerModel> = AsyncListDiffer(this, DiffUtilAnswerModelItemCallback())

    private var selectedItem: AnswerModel? = null
    var isRightAnswerShown = false

    fun getSelectedItem(): AnswerModel = selectedItem
        ?: TempEntitiesFabric.createTempAnswer().apply { isRightAnswer = false }

    fun showRightAnswer() {
        if (!isRightAnswerShown) {
            isRightAnswerShown = true
            differ.currentList.filter { answer -> answer.isRightAnswer }.forEach { answer ->
                redrawItem(answer)
            }
        }
    }

    private fun redrawItem(item: AnswerModel) {
        notifyItemChanged(differ.currentList.indexOf(item))
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

        private fun getItemColor(item: AnswerModel): Int = getBackgroundColorResource(item)

        private fun getBackgroundColorResource(item: AnswerModel): Int = getThemeColor(view.context, when {
            isRightAnswerShown && item.isRightAnswer -> R.attr.themeColorRightAnswer
            selectedItem == item -> R.attr.themeColorAccent
            else -> R.attr.themeColorForegroundElementsBackground
        })

        private fun getThemeColor(context: Context, @AttrRes attributeId: Int): Int {
            val value = TypedValue()
            context.theme.resolveAttribute(attributeId, value, true)
            return value.data
        }
    }

    class DiffUtilAnswerModelItemCallback : BaseDiffUtilItemCallback<AnswerModel>() {
        override fun areContentsTheSame(oldItem: AnswerModel, newItem: AnswerModel): Boolean =
                oldItem == newItem
    }
}