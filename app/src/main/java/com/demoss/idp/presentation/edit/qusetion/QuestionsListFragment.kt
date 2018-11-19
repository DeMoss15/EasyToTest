package com.demoss.idp.presentation.edit.qusetion

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.demoss.idp.R
import com.demoss.idp.base.BaseFragment
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.presentation.adapter.QuestionsRecyclerViewAdapter
import com.demoss.idp.util.ExtraConstants
import com.demoss.idp.util.withArguments
import kotlinx.android.synthetic.main.fragment_questions_list.*
import org.koin.android.ext.android.inject

class QuestionsListFragment : BaseFragment<QuestionsListContract.Presenter>(), QuestionsListContract.View {

    companion object {
        fun newInstance(testId: Int): QuestionsListFragment = QuestionsListFragment()
            .withArguments(ExtraConstants.EXTRA_TEST_ID to testId)
    }

    override val presenter by inject<QuestionsListContract.Presenter>()
    override val layoutResourceId = R.layout.fragment_questions_list
    private val rvAdapter = QuestionsRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            presenter.init(it.getInt(ExtraConstants.EXTRA_TEST_ID))
       }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvQuestions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
    }

    override fun showEmptyProgress(show: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyError(show: Boolean, error: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyView(show: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showData(show: Boolean, data: List<QuestionModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorMessage(error: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRefreshProgress(show: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPageProgress(show: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
