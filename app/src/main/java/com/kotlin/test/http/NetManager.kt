package com.kotlin.test.http

/**
 * @Title NetManager
 * @Description:
 * @Author: alvin
 * @Date: 2017/10/12.13:23
 * @E-mail: 49467306@qq.com
 */
class NetManager private constructor() {
    companion object {
        @Volatile
        var mInstance: NetManager? = null
        fun getInstance(): NetManager {
            if (mInstance == null) {
                synchronized(NetManager::class) {
                    if (mInstance == null) {
                        mInstance = NetManager()
                    }
                }
            }

            return mInstance!!
        }
    }

}