package com.kotlin.test.adapter

import com.kotlin.test.adapter.base.BaseViewHolder
import com.kotlin.test.adapter.base.ItemViewDelegate

/**
 * @Title CommonAdapter
 * @Description:
 * @Author: alvin
 * @Date: 2017/6/30.14:15
 * @E-mail: 49467306@qq.com
 */
open abstract class CommonAdapter<T> : MultiItemCommonAdapter<T> {
    private var mDatas: List<T>? = null
    private var mLayoutId: Int

    constructor(layoutId: Int, datas: List<T>) : super(datas) {
        mDatas = datas
        mLayoutId = layoutId
        addItemDelegate(object : ItemViewDelegate<T> {
            override fun getViewItemLayoutId(): Int {
                return layoutId
            }

            override fun isForViewType(item: T, position: Int): Boolean {
                return true
            }

            override fun convert(holder: BaseViewHolder, item: T, position: Int) {
                this@CommonAdapter.convert(holder, item, position)
            }

        })
    }

    protected abstract fun convert(holder: BaseViewHolder, item: T, position: Int)


}