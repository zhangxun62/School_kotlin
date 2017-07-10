package com.kotlin.test.app

import android.app.Application
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
    }
}