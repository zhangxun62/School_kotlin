package com.kotlin.test.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.kotlin.test.adapter.base.BaseViewHolder
import com.kotlin.test.adapter.base.ItemViewDelegate
import com.kotlin.test.adapter.base.ItemViewDelegateManager

/**
 * @Title MultiItemCommonAdapter
 * @Description:
 * @Author: alvin
 * @Date: 2017/7/10.10:57
 * @E-mail: 49467306@qq.com
 */
open class MultiItemCommonAdapter<T> : RecyclerView.Adapter<BaseViewHolder> {
    private var mDatas: List<T>? = null
    private var mItemViewDelegateManager: ItemViewDelegateManager<T>

    constructor(list: List<T>) : super() {
        mDatas = list
        mItemViewDelegateManager = ItemViewDelegateManager()
    }

    override fun getItemCount(): Int {
        if (mDatas == null)
            return 0
        return mDatas!!.size
    }

    override fun getItemViewType(position: Int): Int {
        if (!useItemViewDelegateManager())
            return super.getItemViewType(position)
        return mItemViewDelegateManager.getItemViewType(mDatas!![position], position)
    }

    fun useItemViewDelegateManager(): Boolean {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        var itemViewDelegate: ItemViewDelegate<T> = mItemViewDelegateManager.getItemViewDelegate(viewType)
        var layoutId: Int = itemViewDelegate.getViewItemLayoutId()
        var viewHolder: BaseViewHolder = BaseViewHolder.get(parent.context, parent, layoutId)
        return viewHolder
    }

    fun convert(holder: BaseViewHolder, t: T) {
        mItemViewDelegateManager.convert(holder, t, holder.adapterPosition)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        convert(holder, mDatas!![position])
    }

    fun addItemDelegate(delegate: ItemViewDelegate<T>): MultiItemCommonAdapter<T> {
        mItemViewDelegateManager.addDelegate(delegate)
        return this
    }

    fun addItemDelegate(viewType: Int, delegate: ItemViewDelegate<T>): MultiItemCommonAdapter<T> {
        mItemViewDelegateManager.addDelegate(viewType, delegate)
        return this
    }

}