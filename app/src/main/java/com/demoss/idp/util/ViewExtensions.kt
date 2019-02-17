package com.demoss.idp.util

import android.widget.SeekBar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.demoss.idp.base.BaseRecyclerViewAdapter
import com.demoss.idp.presentation.adapter.SimpleSwipeItemCallback

fun SeekBar.setOnProgressChangeListener(listener: (Int) -> Unit) {
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            listener(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    })
}

fun RecyclerView.addItemTouchHelperWithCallback(callback: ItemTouchHelper.SimpleCallback) {
    context?.let {
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(this)
    }
}

fun <T> RecyclerView.setupSwipeToDelete(adapter: BaseRecyclerViewAdapter<T, *>, onItemDeleteAction: (T) -> Unit) {
    this.addItemTouchHelperWithCallback(SimpleSwipeItemCallback { itemPosition ->
        adapter.apply {
            onItemDeleteAction(differ.currentList[itemPosition])
            differ.submitList(differ.currentList.toMutableList().apply { removeAt(itemPosition) })
        }
    })
}