package com.demoss.idp.util

import android.widget.SeekBar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.demoss.idp.base.BaseRecyclerViewAdapter
import com.demoss.idp.presentation.adapter.SwipeItemToActionCallback

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

fun RecyclerView.addItemTouchHelperWithCallback(callback: SwipeItemToActionCallback) {
    context?.let {
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(this)
    }
}

fun <T>RecyclerView.setupSwipeToDelete(adapter: BaseRecyclerViewAdapter<T,*,*>, onItemDeleteAction: (T) -> Unit) {
    this.addItemTouchHelperWithCallback(SwipeItemToActionCallback {
        adapter.apply {
            onItemDeleteAction(data[it])
            notifyItemRemoved(it)
            data.removeAt(it)
        }
    })
}