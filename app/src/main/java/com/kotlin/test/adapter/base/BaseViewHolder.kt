package com.kotlin.test.adapter.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * @Title BaseViewHolder
 * @Description:
 * @Author: alvin
 * @Date: 2017/6/30.14:16
 * @E-mail: 49467306@qq.com
 */
class BaseViewHolder : RecyclerView.ViewHolder {
    private var mConvertView: View? = null
    private var mViews: SparseArray<View>? = null

    constructor(itemView: View, parent: ViewGroup) : super(itemView) {
        mConvertView = itemView
        mViews = SparseArray<View>()
    }

    /**
     * 通过id获取View
     * @param viewId
     */
    fun <T : View> getView(viewId: Int): T {
        var view = mViews!!.get(viewId)
        if (view == null) {
            view = mConvertView!!.findViewById(viewId)
            mViews!!.put(viewId, view)
        }

        return view as T
    }

    /**
     * 设置字符
     * @param viewId
     * @param str
     */
    fun setText(viewId: Int, str: String): BaseViewHolder {
        getView<TextView>(viewId).text = str
        return this
    }

    companion object {
        fun get(context: Context, parent: ViewGroup, layoutId: Int): BaseViewHolder {
            var itemView = LayoutInflater.from(context).inflate(layoutId, parent, false) as View
            return BaseViewHolder(itemView, parent)
        }
    }
}