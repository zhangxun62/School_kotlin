package com.kotlin.test.adapter.base

import android.support.v4.util.SparseArrayCompat

/**
 * @Title ItemViewDelegateManager
 * @Description:
 * @Author: alvin
 * @Date: 2017/7/10.11:14
 * @E-mail: 49467306@qq.com
 */
class ItemViewDelegateManager<T> {
    private var delegates: SparseArrayCompat<ItemViewDelegate<T>> = SparseArrayCompat()
    fun getItemViewDelegateCount(): Int {
        return delegates.size()
    }

    fun addDelegate(delegate: ItemViewDelegate<T>): ItemViewDelegateManager<T> {
        var itemType: Int = delegates.size()
        if (delegate != null) {
            delegates.put(itemType, delegate)
            itemType++
        }
        return this
    }

    fun addDelegate(itemType: Int, delegate: ItemViewDelegate<T>): ItemViewDelegateManager<T> {
        if (delegates.get(itemType) != null) {
            throw IllegalArgumentException("An ItemViewDelegate is already registered for the viewType = "
                    + itemType
                    + ". Already registered ItemViewDelegate is "
                    + delegates.get(itemType))
        }
        delegates.put(itemType, delegate)
        return this
    }

    fun removeDelegate(delegate: ItemViewDelegate<T>): ItemViewDelegateManager<T> {
        if (delegate == null)
            throw NullPointerException("ItemViewDelegate is null")
        var indexToRemove: Int = delegates.indexOfValue(delegate)
        if (indexToRemove >= 0)
            delegates.removeAt(indexToRemove)
        return this
    }

    fun removeDelegate(itemType: Int): ItemViewDelegateManager<T> {
        var indexToRemove: Int = delegates.indexOfKey(itemType)
        if (indexToRemove >= 0)
            delegates.removeAt(indexToRemove)
        return this
    }

    fun getItemViewType(item: T, position: Int): Int {
        var size: Int = getItemViewDelegateCount() - 1
        while (size >= 0) {
            var delegate: ItemViewDelegate<T> = delegates.valueAt(size)
            if (delegate.isForViewType(item, position)) {
                return delegates.keyAt(size)
            }
            size--

        }
        throw IllegalArgumentException(
                "No ItemViewDelegate added that matches position="
                        + position + " in data source")
    }

    fun convert(holder: BaseViewHolder, item: T, position: Int) {
        var size: Int = getItemViewDelegateCount()
        var i: Int = 0
        while (i < size) {
            var delegate: ItemViewDelegate<T> = delegates.valueAt(i)
            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position)
                return
            }
            i++
        }
        throw IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position="
                        + position + " in data source")
    }

    fun getItemViewDelegate(itemType: Int): ItemViewDelegate<T> {
        return delegates.get(itemType)
    }

    fun getItemViewLayoutId(itemType: Int): Int {
        return getItemViewDelegate(itemType).getViewItemLayoutId()
    }

    fun getItemType(delegate: ItemViewDelegate<T>): Int {
        return delegates.indexOfValue(delegate)
    }
}


