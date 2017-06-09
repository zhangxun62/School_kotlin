package com.kotlin.test.ui.activity

import android.view.View
import com.kotlin.test.R
import com.kotlin.test.ui.fragment.HomeFragment

/**
 * 主界面
 */
class MainActivity : BaseActivity() {


    override fun getContentView(): View? {
        return null
    }

    override fun getContentViewLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        addFragment(R.id.id_container, HomeFragment())
    }

    override fun initDatas() {
    }

    /**
     * 控件的单击监听
     * @param v
     */
    override fun onClick(v: View) {
        when (v.id) {

        }
    }


}
