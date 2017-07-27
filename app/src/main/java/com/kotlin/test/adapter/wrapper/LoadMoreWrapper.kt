package com.kotlin.test.adapter.wrapper

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kotlin.test.adapter.base.BaseViewHolder

/**
 * @Title LoadMoreWrapper
 * @Description:
 * @Author: alvin
 * @Date: 2017/7/27.16:34
 * @E-mail: 49467306@qq.com
 */
class LoadMoreWrapper : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    var mLoadMoreView: View? = null
    var mLoadMoreLayoutId: Int? = null
    var mLoadMoreListener: OnLoadMoreListener? = null

    constructor(mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        this.mAdapter = mAdapter
    }

    fun hasLoadMore(): Boolean {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0
    }

    fun isShowLoadMore(position: Int): Boolean {
        return hasLoadMore() && (position >= mAdapter.itemCount)
    }

    override fun getItemViewType(position: Int): Int {
        if (isShowLoadMore(position))
            return ITEM_TYPE_LOAD_MORE
        return mAdapter.getItemViewType(position)

    }

    override fun getItemCount(): Int {
        if (hasLoadMore())
            return mAdapter.itemCount + 1
        return mAdapter.itemCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            var holder: BaseViewHolder
            if (mLoadMoreView != null) {
                holder = BaseViewHolder.Companion.createViewHolder(mLoadMoreView!!)
            } else {
                holder = BaseViewHolder.Companion.createViewHolder(parent.context, parent, mLoadMoreLayoutId!!)
            }
            return holder
        }
        return mAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (isShowLoadMore(position)) {
            if (mLoadMoreListener != null) {
                mLoadMoreListener!!.onLoadMoreRequested()
            }
            return
        }
        mAdapter.onBindViewHolder(holder, position)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mAdapter, recyclerView, object : WrapperUtils.SpanSizeCallback {
            override fun getSpanSize(layoutManager: GridLayoutManager, oldLookup: GridLayoutManager.SpanSizeLookup, position: Int): Int {
                if (isShowLoadMore(position)) {
                    return layoutManager.spanCount
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position)
                }
                return 1
            }

        })
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        mAdapter!!.onViewAttachedToWindow(holder)
        var position = holder.layoutPosition
        if (isShowLoadMore(position)) {
            WrapperUtils.setFullSpan(holder)
        }
    }

    interface OnLoadMoreListener {
        fun onLoadMoreRequested()
    }

    fun setLoadMoreView(view: View): LoadMoreWrapper {
        this.mLoadMoreView = view
        return this
    }

    fun setLoadMoreView(layoutId: Int): LoadMoreWrapper {
        this.mLoadMoreLayoutId = layoutId
        return this
    }

    fun setLoadMoreListener(listener: OnLoadMoreListener): LoadMoreWrapper {
        this.mLoadMoreListener = listener
        return this
    }

    companion object {
        val ITEM_TYPE_LOAD_MORE = Int.MAX_VALUE - 2
    }
}