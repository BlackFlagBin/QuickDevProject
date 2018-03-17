package com.blackflagbin.kcommon.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.blackflagbin.kcommon.R
import com.blackflagbin.kcommon.facade.CommonLibrary
import com.blackflagbin.kcommon.widget.CustomLoadMoreView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kennyc.view.MultiStateView

/**
 * Created by blackflagbin on 2017/9/20.
 */

abstract class BaseRefreshAndLoadMoreActivity<out A, out C, P : IBaseRefreshAndLoadMorePresenter, D> :
        BaseActivity<A, C, P, D>(), BaseQuickAdapter.RequestLoadMoreListener,
        IBaseRefreshAndLoadMoreView<D> {
    protected var mAdapter: BaseQuickAdapter<*, *>? = null
    protected var mIsLoadComplete = false
    protected var mCurPage = CommonLibrary.instance.startPage
    protected val PAGE_SIZE by lazy { CommonLibrary.instance.pageSize }
    protected lateinit var mRecyclerView: RecyclerView
    protected lateinit var mLayoutManager: RecyclerView.LayoutManager

    protected val emptyLayoutId: Int?
        get() = R.layout.layout_empty

    protected abstract val adapter: BaseQuickAdapter<*, *>

    protected abstract val recyclerView: RecyclerView

    protected abstract val layoutManager: RecyclerView.LayoutManager

    override fun initView() {
        super.initView()
        mRecyclerView = recyclerView
        mLayoutManager = layoutManager
        mRecyclerView.setLayoutManager(mLayoutManager)
        mAdapter = adapter
        val noDataView = getLayoutInflater().inflate(
                emptyLayoutId!!, mRecyclerView.getParent() as ViewGroup, false)
        mAdapter?.setEmptyView(noDataView)
        mAdapter?.setLoadMoreView(CustomLoadMoreView())
        mAdapter?.setOnLoadMoreListener(this, mRecyclerView)
        mRecyclerView.setAdapter(mAdapter)
        mAdapter?.disableLoadMoreIfNotFullPage()
    }

    override fun showSuccessView(data: D) {
        mMultiStateView?.setViewState(MultiStateView.VIEW_STATE_CONTENT)
        mAdapter?.replaceData(data as List<*>)
        showContentView(data)
    }

    override fun beforeInitData() {
        if (mAdapter != null) {
            mAdapter!!.setEnableLoadMore(false)
        }
        mIsLoadComplete = false
        mCurPage = 1
    }

    fun afterLoadMore(list: D) {
        if ((list as List<*>).size < PAGE_SIZE) {
            mIsLoadComplete = true
        } else {
            mIsLoadComplete = false
        }
        if ((list as List<*>).size != 0) {
            mAdapter?.addData(list as List<*>)
        }
        mCurPage++
        mAdapter?.loadMoreComplete()
        mSwipeRefresh?.setEnabled(true)
    }

    override fun afterLoadMoreError(e: Throwable) {
        mAdapter!!.loadMoreFail()
        mSwipeRefresh!!.setEnabled(true)
    }

    override fun onLoadMoreRequested() {
        mSwipeRefresh!!.setEnabled(false)
        if (mAdapter!!.data.size < PAGE_SIZE) {
            mAdapter!!.loadMoreEnd(false)
            mSwipeRefresh!!.setEnabled(true)
        } else {
            if (mIsLoadComplete) {
                mAdapter!!.loadMoreEnd()
                mSwipeRefresh!!.setEnabled(true)
            } else {
                mPresenter.initData(mDataMap, mCurPage + 1)
            }
        }
    }

}
