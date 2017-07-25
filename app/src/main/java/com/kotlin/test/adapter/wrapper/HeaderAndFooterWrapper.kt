package com.kotlin.test.adapter.wrapper

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kotlin.test.adapter.base.BaseViewHolder

/**
 * @Title HeaderAndFooterWrapper
 * @Description:
 * @Author: alvin
 * @Date: 2017/7/25.14:48
 * @E-mail: 49467306@qq.com
 */
class HeaderAndFooterWrapper : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    var mHeaderViews: SparseArrayCompat<View> = SparseArrayCompat<View>()
    var mFooterViews: SparseArrayCompat<View> = SparseArrayCompat<View>()
    var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    constructor(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        mAdapter = adapter
    }

    /**
     * 根据position判断item 是否是头布局
     * @param position
     */
    fun isHeaderViewPos(position: Int): Boolean {
        return position < getHeaderViewCount()
    }

    /**
     * 根据position判断item 是否是尾布局
     * @param position
     */
    fun isFooterViewPos(position: Int): Boolean {
        return position >= getHeaderViewCount() + getRealItemCount()
    }

    fun getHeaderViewCount(): Int {
        return mHeaderViews.size()
    }

    /**
     * 获取真正主item的数量
     */
    fun getRealItemCount(): Int {
        return mAdapter.itemCount
    }

    fun getFooterViewCount(): Int {
        return mFooterViews.size()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (mHeaderViews.get(viewType) != null) {
            return BaseViewHolder.createViewHolder(mHeaderViews[viewType])
        } else if (mFooterViews[viewType] != null) {
            return BaseViewHolder.createViewHolder(mFooterViews[viewType])
        }
        return mAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (isHeaderViewPos(position)) {
            return
        } else if (isFooterViewPos(position)) {
            return
        }
        return mAdapter.onBindViewHolder(holder, position)

    }

    override fun getItemCount(): Int {
        return getHeaderViewCount() + getFooterViewCount() + getRealItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position)
        } else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - getHeaderViewCount() - getRealItemCount())
        }
        return mAdapter.getItemViewType(position)
    }

    /**
     * 添加HeaderView
     * @param view
     */
    fun addHeaderView(view: View) {
        mHeaderViews.put(mHeaderViews.size() + ITEM_TYPE_HEADER, view)
    }

    /**
     * 添加FooterView
     * @param view
     */
    fun addFooterView(view: View) {
        mFooterViews.put(mFooterViews.size() + ITEM_TYPE_FOOTER, view)
    }

    companion object {
        val ITEM_TYPE_HEADER = 10000
        val ITEM_TYPE_FOOTER = 20000
    }
}