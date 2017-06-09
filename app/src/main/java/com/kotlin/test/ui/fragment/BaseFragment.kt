package com.kotlin.test.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @Title BaseFragment
 * @Description:
 * @Author: alvin
 * @Date: 2017/6/9.15:41
 * @E-mail: 49467306@qq.com
 */
abstract class BaseFragment : Fragment() {
    protected abstract fun getFragmentView(): View?
    protected abstract fun getFragmentViewByLayoutId(): Int
    private var mView: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (getFragmentView() != null) {
            mView = getFragmentView()
            return mView
        }
        mView = inflater.inflate(getFragmentViewByLayoutId(), container, false)
        return mView
    }
}