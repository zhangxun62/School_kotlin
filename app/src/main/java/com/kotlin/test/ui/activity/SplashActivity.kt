package com.kotlin.test.ui.activity

import android.support.v7.widget.AppCompatImageView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kotlin.test.base.BaseActivity
import com.kotlin.test.util.Preference

/**
 * @Title SplashActivity
 * @Description:
 * @author: alvin
 * @Date: 2018/2/27.15:58
 * @E-mail: 49467306@qq.com
 */
class SplashActivity : BaseActivity() {

    override fun getContentView(): View? {
        var imageView = AppCompatImageView(this)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return imageView
    }

    override fun getContentViewLayoutId(): Int {
        return 0
    }

    override fun initViews() {
    }

    override fun initData() {
        var isFirstRun: Boolean by Preference(this, "isFirstRun", true)
        if (isFirstRun) {
            isFirstRun = false
        } else {
            Thread {
                Thread.sleep(2000)
                startActivity(LoginActivity::class.java)
                finish()
            }.start()
        }

//        startActivity(LoginActivity::class.java)
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}