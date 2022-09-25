package cm.module.core.plugins.refresh

import androidx.databinding.ObservableArrayList
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * ****************************************************************
 * Author: Chaman
 * Date: 2022/7/22 11:00
 * Desc:
 * *****************************************************************
 */
abstract class RefreshManager<T> {
    private var data: ObservableArrayList<T>? = null
    var refreshLayout: SmartRefreshLayout? = null
        set(value) {
            field = value
            value?.setEnableLoadMore(canLoadMore)
            value?.setEnableRefresh(canRefresh)
        }
    private var page: Int = 1
    private var PAGE_SIZE: Int = 20
    var canLoadMore: Boolean = true
    private var canRefresh: Boolean = true
    private var isRefresh: Boolean = true

    constructor(
        data: ObservableArrayList<T>?,
        canRefresh: Boolean = true,
        canLoadMore: Boolean = true
    ) {
        this.data = data
        this.canLoadMore = canLoadMore
        this.canRefresh = canRefresh
    }

    constructor(
        data: ObservableArrayList<T>?,
        page_size: Int = 20
    ) {
        this.data = data
        this.PAGE_SIZE = page_size
    }


    abstract fun loadData(isRefresh: Boolean, page: Int, page_size: Int)


    fun onRefresh() {
        page = 1
        isRefresh = true
        loadData(true, page, PAGE_SIZE)
    }

    fun onLoadMore() {
        page++
        isRefresh = false
        loadData(false, page, PAGE_SIZE)
    }

    private fun onLoadMoreSuccess(data: MutableList<T>, loadPage: Int? = -1, totalSize: Int? = -1) {
        if ((loadPage ?: 0) > 0) {
            page = loadPage ?: 0
        }
        this.data?.addAll(data)
        refreshLayout?.finishLoadMore()
        if ((totalSize ?: 0) > 0) {
            refreshLayout?.setEnableLoadMore((this.data?.size ?: 0) < (totalSize ?: 0))
        }
    }

    fun onLoadMoreFailed() {
        page--
        refreshLayout?.finishLoadMore(false)
    }

    private fun onRefreshSuccess(data: MutableList<T>, totalSize: Int? = -1) {
        this.data?.clear()
        this.data?.addAll(data)
        refreshLayout?.finishRefresh()
        if ((totalSize ?: 0) > 0) {
            refreshLayout?.setEnableLoadMore((this.data?.size ?: 0) < (totalSize ?: 0))
        }
    }

    fun onRefreshFailed() {
        page = 1
        refreshLayout?.finishRefresh(false)
    }

//    fun onSuccess(isRefresh: Boolean, data: ListResponse<T>) {
//        if (isRefresh) {
//            if ((data.records?.size ?: 0) >= 0) {
//                onRefreshSuccess(data.records as MutableList<T>, data.total)
//            } else {
//                onRefreshFailed()
//            }
//        } else {
//            if ((data.records?.size ?: 0) > 0) {
//                onLoadMoreSuccess(
//                    data.records as MutableList<T>,
//                    data.pages,
//                    data.total
//                )
//            } else {
//                onLoadMoreFailed()
//            }
//        }
//    }

    fun onSuccess(isRefresh: Boolean, data: MutableList<T>) {
        if (isRefresh) {
            if ((data.size ?: 0) >= 0) {
                onRefreshSuccess(data)
            } else {
                onRefreshFailed()
            }
        } else {
            if ((data?.size ?: 0) > 0) {
                onLoadMoreSuccess(data)
            } else {
                onLoadMoreFailed()
            }
        }
    }

    fun onFailed(isRefresh: Boolean) {
        if (isRefresh) {
            onRefreshFailed()
        } else {
            onLoadMoreFailed()
        }
    }

}