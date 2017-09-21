package com.kotlin.test.entity

/**
 * @Title HttpResponse
 * @Description:
 * @Author: alvin
 * @Date: 2017/9/21.09:55
 * @E-mail: 49467306@qq.com
 */
data class HttpResponse<T>(
        var msg: String,
        var data: T
)