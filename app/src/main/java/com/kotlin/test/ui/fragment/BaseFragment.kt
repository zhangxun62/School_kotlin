package com.kotlin.test.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * @Title BaseFragment
 * @Description:
 * @Author: alvin
 * @Date: 2017/6/9.15:41
 * @E-mail: 49467306@qq.com
 */
abstract class BaseFragment : Fragment(), View.OnClickListener {
    protected abstract fun getFragmentView(): View?
    protected abstract fun getFragmentViewByLayoutId(): Int
    private var mView: View? = null
    protected abstract fun initData()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (getFragmentView() != null) {
            mView = getFragmentView()
        }
        if (mView == null)
            mView = inflater.inflate(getFragmentViewByLayoutId(), container, false)

        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    /**
     * 显示Toast提示框
     * @param msg
     */
    fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}