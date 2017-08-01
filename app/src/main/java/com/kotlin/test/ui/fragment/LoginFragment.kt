package com.kotlin.test.ui.fragment

import android.view.View
import com.kotlin.test.R
import com.kotlin.test.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * @Title LoginFragment
 * @Description:
 * @Author: alvin
 * @Date: 2017/7/31.16:10
 * @E-mail: 49467306@qq.com
 */
class LoginFragment : BaseFragment() {
    override fun getFragmentView(): View? {
        return null
    }

    override fun getFragmentViewByLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initData() {
        id_tv_register.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.id_tv_register -> {
                (context as BaseActivity).addFragment(R.id.id_container, RegisterFragment())
            }
            else -> {
            }
        }
    }
}