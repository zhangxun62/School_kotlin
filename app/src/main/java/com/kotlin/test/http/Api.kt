package com.kotlin.test.http

import com.kotlin.test.entity.HttpResponse
import com.kotlin.test.entity.Question
import com.kotlin.test.entity.UserInfo
import io.reactivex.Flowable
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * @Title Api
 * @Description:
 * @Author: alvin
 * @Date: 2017/8/1.14:54
 * @E-mail: 49467306@qq.com
 */
interface Api {

    @POST("restful/users")
    @Headers("Content-Type:application/json") //需要添加头
    fun login(@Body body: RequestBody): Flowable<HttpResponse<UserInfo>>

    @POST("restful/users")
    @Headers("Content-Type:application/json") //需要添加头
    fun register(@Body body: RequestBody): Flowable<HttpResponse<Object>>

    @POST("restful/users")
    @Headers("Content-Type:application/json") //需要添加头
    fun loginByToken(@Body body: RequestBody): Flowable<HttpResponse<Object>>

    @GET("restful/questions/")
    fun getQuestionsList(@Query("page") page: Int): Flowable<HttpResponse<List<Question>>>

    @GET("restful/questions/{id}")
    fun getQuestionsView(@Path("id") id: Int): Flowable<HttpResponse<Question>>
}