package com.kotlin.test.adapter.wrapper

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kotlin.test.adapter.base.BaseViewHolder

/**
 * @Title EmptyWrapper
 * @Description:
 * @Author: alvin
 * @Date: 2017/7/25.10:41
 * @E-mail: 49467306@qq.com
 */
class EmptyWrapper<T> : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    var mEmptyView: View? = null
    var mEmptyLayoutId: Int? = null

    constructor(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        mAdapter = adapter
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (isEmpty()) {
            var holder: BaseViewHolder
            if (mEmptyView != null) {
                holder = BaseViewHolder.createViewHolder(mEmptyView!!)
            } else {
                holder = BaseViewHolder.Companion.createViewHolder(parent!!.context, parent, mEmptyLayoutId!!)
            }
            return holder
        }
        return mAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (isEmpty()) {
            return
        }
        mAdapter.onBindViewHolder(holder, position)
    }


    override fun getItemViewType(position: Int): Int {
        if (isEmpty()) {
            return ITEM_TYPE_EMPTY
        }
        return mAdapter.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        if (isEmpty())
            return 1
        return mAdapter.itemCount
    }

    private fun isEmpty(): Boolean {
        return (mEmptyView != null || mEmptyLayoutId != 0) && mAdapter.itemCount == 0
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mAdapter, recyclerView, object : WrapperUtils.SpanSizeCallback {
            override fun getSpanSize(layoutManager: GridLayoutManager, oldLookup: GridLayoutManager.SpanSizeLookup, position: Int): Int {
                if (isEmpty()) {
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
        if (isEmpty()) {
            WrapperUtils.setFullSpan(holder)
        }
    }

    fun setEmptyView(view: View) {
        mEmptyView = view
    }

    fun setEmptyView(layoutId: Int) {
        mEmptyLayoutId = layoutId
    }

    companion object {
        val ITEM_TYPE_EMPTY: Int = Int.MAX_VALUE - 1
    }
}