package com.demoss.idp.presentation.adapter

import com.demoss.idp.base.BaseRecyclerViewAdapter
import com.demoss.idp.domain.model.QuestionModel

class QuestionsDiffUtilCallback(oldList: List<QuestionModel>, newList: List<QuestionModel>):
    BaseRecyclerViewAdapter.BaseDiffUtilCallback<QuestionModel>(oldList, newList) {
}