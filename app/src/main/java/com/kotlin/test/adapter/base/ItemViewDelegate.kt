package com.kotlin.test.adapter.base

/**
 * @Title ItemViewDelegate
 * @Description:
 * @Author: alvin
 * @Date: 2017/7/10.11:10
 * @E-mail: 49467306@qq.com
 */
interface ItemViewDelegate<T> {
    fun getViewItemLayoutId(): Int
    fun isForViewType(item: T, position: Int): Boolean
    fun convert(holder: BaseViewHolder, item: T, position: Int)
}