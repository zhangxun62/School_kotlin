package com.kotlin.test.http

import com.kotlin.test.entity.HttpResponse
import com.kotlin.test.entity.UserInfo
import io.reactivex.Flowable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * @Title Api
 * @Description:
 * @Author: alvin
 * @Date: 2017/8/1.14:54
 * @E-mail: 49467306@qq.com
 */
interface Api {
    @POST("restful/users/login")
    @Headers("Content-Type:application/json") //需要添加头
    fun login(@Body body: RequestBody): Flowable<HttpResponse<UserInfo>>

    @POST("restful/users/register")
    @Headers("Content-Type: application/json") //需要添加头
    fun register(@Body body: RequestBody): Flowable<String>
}