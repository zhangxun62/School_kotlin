package com.kotlin.test.widget.mdtablayout.adapter

import android.database.DataSetObserver
import android.graphics.drawable.Drawable

/**
 * @Title TabAdapter
 * *
 * @Description:
 * *
 * @Author: alvin
 * *
 * @Date: 2016/10/26.16:08
 * *
 * @E-mail: 49467306@qq.com
 */
abstract class TabAdapter {
    private var mObserver: DataSetObserver? = null

    /**
     * 注册数据变化
     * @param observer
     */
    fun registerObserver(observer: DataSetObserver) {
        mObserver = observer
    }

    /**
     * 取消注册
     */
    fun unregisterObserver() {
        mObserver = null
    }

    fun notifyDataSetChanged() {
        if (mObserver != null) mObserver!!.onChanged()
    }

    fun notifyDataSetInvalidate() {
        if (mObserver != null) mObserver!!.onInvalidated()
    }

    abstract val itemCount: Int

    abstract fun getImage(position: Int): Drawable

    abstract fun getText(position: Int): CharSequence
}
