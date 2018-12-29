package com.demoss.idp.presentation.main.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.demoss.idp.R
import com.demoss.idp.util.EmptyConstants
import kotlinx.android.synthetic.main.fragment_dialog_add_test.*

class SimpleItemsListDialogFragment : DialogFragment() {

    private var title: String = EmptyConstants.EMPTY_STRING
    private lateinit var rvAdapter: SimpleItemsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_dialog_add_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvTitle.text = title
        rvItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
    }

    fun setupFragment(title: String, itemsList: List<String>, onItemClick: (String) -> Unit) {
        this.title = title
        rvAdapter = SimpleItemsListAdapter {
            onItemClick(it)
            dismiss()
        }
        rvAdapter.dispatchData(itemsList)
    }

    class Builder() {

        var title: String = EmptyConstants.EMPTY_STRING
        var itemsList: List<String> = listOf()
        var onClickListener: (String) -> Unit = {}

        fun build(): SimpleItemsListDialogFragment {
            val fragment = SimpleItemsListDialogFragment()
            fragment.setupFragment(title, itemsList, onClickListener)
            return fragment
        }
    }
}