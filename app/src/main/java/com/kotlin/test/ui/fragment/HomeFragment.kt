package com.kotlin.test.ui.fragment

import android.view.View
import com.kotlin.test.R

/**
 * @Title HomeFragment
 * @Description:
 * @Author: alvin
 * @Date: 2017/6/9.16:53
 * @E-mail: 49467306@qq.com
 */
class HomeFragment :BaseFragment(){
    override fun getFragmentView(): View? {
       return null
    }

    override fun getFragmentViewByLayoutId(): Int {
        return R.layout.fragment_home
    }
}