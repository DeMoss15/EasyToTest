package com.demoss.idp.presentation.adapter

import com.demoss.idp.base.BaseRecyclerViewAdapter
import com.demoss.idp.domain.model.AnswerModel

class AnswersDiffUtilCallback(oldList: List<AnswerModel>, newList: List<AnswerModel>)
    : BaseRecyclerViewAdapter.BaseDiffUtilCallback<AnswerModel>(oldList, newList)