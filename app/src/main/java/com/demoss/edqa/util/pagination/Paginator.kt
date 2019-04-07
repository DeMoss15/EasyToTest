package com.demoss.edqa.util.pagination

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class Paginator<T>(
    responseObservable: Observable<List<T>>,
    private val viewController: ViewController<T>,
    private val requestFactory: (Int) -> Unit
) {

    interface ViewController<T> {
        fun showEmptyProgress(show: Boolean)
        fun showEmptyError(show: Boolean, error: Throwable? = null)
        fun showEmptyView(show: Boolean)
        fun showData(show: Boolean, data: List<T> = emptyList())
        fun showErrorMessage(error: Throwable)
        fun showRefreshProgress(show: Boolean)
        fun showPageProgress(show: Boolean)
    }

    private val FIRST_PAGE = 1

    private var currentState: State<T> = EMPTY()
    private var currentPage = 0
    private val currentData = mutableListOf<T>()
    private var disposable: Disposable? = null

    init {
        disposable = responseObservable.subscribe(
            { currentState.newData(it) },
            { currentState.fail(it) }
        )
    }

    fun restart() {
        currentState.restart()
    }

    fun refresh() {
        currentState.refresh()
    }

    fun loadNewPage() {
        currentState.loadNewPage()
    }

    fun release() {
        currentState.release()
    }

    private fun loadPage(page: Int) {
        requestFactory(page)
    }

    private interface State<T> {
        fun restart() {}
        fun refresh() {}
        fun loadNewPage() {}
        fun release() {}
        fun newData(data: List<T>) {}
        fun fail(error: Throwable) {}
    }

    private inner class EMPTY : State<T> {

        override fun refresh() {
            currentState = EMPTY_PROGRESS()
            viewController.showEmptyProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun release() {
            currentState = RELEASED()
        }
    }

    private inner class EMPTY_PROGRESS : State<T> {

        override fun restart() {
            loadPage(FIRST_PAGE)
        }

        override fun newData(data: List<T>) {
            if (data.isNotEmpty()) {
                currentState = DATA()
                currentData.apply {
                    clear()
                    addAll(data)
                }
                currentPage = FIRST_PAGE
                viewController.apply {
                    showData(true, currentData)
                    showEmptyProgress(false)
                }
            } else {
                currentState = EMPTY_DATA()
                viewController.apply {
                    showEmptyProgress(false)
                    showEmptyView(true)
                }
            }
        }

        override fun fail(error: Throwable) {
            currentState = EMPTY_ERROR()
            viewController.apply {
                showEmptyProgress(false)
                showEmptyError(true, error)
            }
        }

        override fun release() {
            currentState = RELEASED()
        }
    }

    private inner class EMPTY_ERROR : State<T> {

        override fun restart() {
            currentState = EMPTY_PROGRESS()
            viewController.apply {
                showEmptyError(false)
                showEmptyProgress(true)
            }
            loadPage(FIRST_PAGE)
        }

        override fun refresh() {
            currentState = EMPTY_PROGRESS()
            viewController.apply {
                showEmptyError(false)
                showEmptyProgress(true)
            }
            loadPage(FIRST_PAGE)
        }

        override fun release() {
            currentState = RELEASED()
        }
    }

    private inner class EMPTY_DATA : State<T> {

        override fun restart() {
            currentState = EMPTY_PROGRESS()
            viewController.apply {
                showEmptyView(false)
                showEmptyProgress(true)
            }
            loadPage(FIRST_PAGE)
        }

        override fun refresh() {
            currentState = EMPTY_PROGRESS()
            viewController.apply {
                showEmptyView(false)
                showEmptyProgress(true)
            }
            loadPage(FIRST_PAGE)
        }

        override fun release() {
            currentState = RELEASED()
        }
    }

    private inner class DATA : State<T> {

        override fun restart() {
            currentState = EMPTY_PROGRESS()
            viewController.apply {
                showData(false)
                showEmptyProgress(true)
            }
            loadPage(FIRST_PAGE)
        }

        override fun refresh() {
            currentState = REFRESH()
            viewController.showRefreshProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun loadNewPage() {
            currentState = PAGE_PROGRESS()
            viewController.showPageProgress(true)
            loadPage(currentPage + 1)
        }

        override fun release() {
            currentState = RELEASED()
        }
    }

    private inner class REFRESH : State<T> {

        override fun restart() {
            currentState = EMPTY_PROGRESS()
            viewController.apply {
                showData(false)
                showRefreshProgress(false)
                showEmptyProgress(true)
            }
            loadPage(FIRST_PAGE)
        }

        override fun newData(data: List<T>) {
            if (data.isNotEmpty()) {
                currentState = DATA()
                currentData.apply {
                    clear()
                    addAll(data)
                }
                currentPage = FIRST_PAGE
                viewController.apply {
                    showRefreshProgress(false)
                    showData(true, currentData)
                }
            } else {
                currentState = EMPTY_DATA()
                currentData.clear()
                viewController.apply {
                    showData(false)
                    showRefreshProgress(false)
                    showEmptyView(true)
                }
            }
        }

        override fun fail(error: Throwable) {
            currentState = DATA()
            viewController.apply {
                showRefreshProgress(false)
                showErrorMessage(error)
            }
        }

        override fun release() {
            currentState = RELEASED()
        }
    }

    private inner class PAGE_PROGRESS : State<T> {

        override fun restart() {
            currentState = EMPTY_PROGRESS()
            viewController.apply {
                showData(false)
                showPageProgress(false)
                showEmptyProgress(true)
            }
            loadPage(FIRST_PAGE)
        }

        override fun newData(data: List<T>) {
            if (data.isNotEmpty()) {
                currentState = DATA()
                currentData.addAll(data)
                currentPage++
                viewController.apply {
                    showPageProgress(false)
                    showData(true, currentData)
                }
            } else {
                currentState = ALL_DATA()
                viewController.showPageProgress(false)
            }
        }

        override fun refresh() {
            currentState = REFRESH()
            viewController.apply {
                showPageProgress(false)
                showRefreshProgress(true)
            }
            loadPage(FIRST_PAGE)
        }

        override fun fail(error: Throwable) {
            currentState = DATA()
            viewController.apply {
                showPageProgress(false)
                showErrorMessage(error)
            }
        }

        override fun release() {
            currentState = RELEASED()
        }
    }

    private inner class ALL_DATA : State<T> {

        override fun restart() {
            currentState = EMPTY_PROGRESS()
            viewController.apply {
                showData(false)
                showEmptyProgress(true)
            }
            loadPage(FIRST_PAGE)
        }

        override fun refresh() {
            currentState = REFRESH()
            viewController.showRefreshProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun release() {
            currentState = RELEASED()
        }
    }

    private inner class RELEASED : State<T> {
        init {
            disposable?.dispose()
        }
    }
}
