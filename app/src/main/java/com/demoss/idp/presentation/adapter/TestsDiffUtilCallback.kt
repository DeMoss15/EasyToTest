package com.demoss.idp.presentation.adapter

import com.demoss.idp.base.BaseRecyclerViewAdapter
import com.demoss.idp.domain.model.TestModel

class TestsDiffUtilCallback(oldList: List<TestModel>, newList: List<TestModel>) :
    BaseRecyclerViewAdapter.BaseDiffUtilCallback<TestModel>(oldList, newList) {

    // TODO: 08.11.18 update comparing

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}