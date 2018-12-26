package com.demoss.idp.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T,
        VH : BaseRecyclerViewAdapter.BaseViewHolder<T>,
        DiffUtilCallback : BaseRecyclerViewAdapter.BaseDiffUtilCallback<T>> : RecyclerView.Adapter<VH>() {

    val data: MutableList<T> = mutableListOf()
    abstract val viewHolderFactory: (view: View) -> VH
    abstract val layoutResId: Int
    abstract val diffUtilCallbackFactory: (oldList: List<T>, newList: List<T>) -> DiffUtilCallback

    // RV Adapter functions ============================================================================================
    final override fun getItemCount(): Int = data.size

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = viewHolderFactory(
        LayoutInflater.from(parent.context)
            .inflate(layoutResId, parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(data[position])
    }

    // DiffUtil applying ===============================================================================================
    fun dispatchData(list: List<T>) {
        with(DiffUtil.calculateDiff(diffUtilCallbackFactory(data, list))) {
            data.clear()
            data.addAll(list)
            dispatchUpdatesTo(this@BaseRecyclerViewAdapter)
        }
    }

    // Abstract classes ================================================================================================
    abstract class BaseDiffUtilCallback<T>(private var oldList: List<T>, private var newList: List<T>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition]?.hashCode() == newList[newItemPosition]?.hashCode()

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    abstract class BaseViewHolder<T>(val view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bindData(item: T)
    }
}