package com.kotlin.test.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
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

    protected abstract fun initData()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = getFragmentView()
        if (view == null) {
            view = inflater.inflate(getFragmentViewByLayoutId(), container, false)
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    /**
     * 显示Toast提示框
     * @param msg
     */
    fun toast(msg: String, flag: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, msg, flag).show()
    }

    inline fun <reified T> debug(log: Any) {
        Log.d(T::class.simpleName, log.toString())
    }

    fun <T : View> findId(id: Int): T {
        if (view == null) {
            throw NullPointerException("rootView is null")
        } else {
            return view!!.findViewById(id) as T
        }
    }

}