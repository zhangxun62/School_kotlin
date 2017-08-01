package com.kotlin.test.app

import android.app.Application
import android.content.Context
import com.tencent.bugly.Bugly

/**
 * @Title MyApplication
 * @Description:
 * @Author: alvin
 * @Date: 2017/7/10.09:21
 * @E-mail: 49467306@qq.com
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Bugly.init(applicationContext, "a3281bb002", false)
        mApp = this
    }


    companion object {
        var mApp: Application? = null
        val context: Context
            get() = mApp!!.applicationContext
    }
}