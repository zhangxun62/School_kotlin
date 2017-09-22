package com.kotlin.test.http

import android.content.Context
import android.net.ConnectivityManager
import com.kotlin.test.app.MyApplication
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @Title HttpClient
 * @Description:
 * @Author: alvin
 * @Date: 2017/8/1.15:02
 * @E-mail: 49467306@qq.com
 */
class HttpClient {
    var mRetrofit: Retrofit? = null
    private fun retrofit(): Retrofit {
        if (mRetrofit == null) {
            var builder = OkHttpClient.Builder()

            /**
             *设置缓存，代码略
             */
            var cacheFile = File(MyApplication.context.cacheDir, "SchoolCache")
            var cache = Cache(cacheFile, (1024 * 1024 * 50).toLong())
            var cacheInterceptor = Interceptor { chain ->
                var request = chain.request()
                if (!isNetWorkAvailable(MyApplication.context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .url(request.url())
                            .build()
                }
                var response: Response = chain.proceed(request)

                if (isNetWorkAvailable(MyApplication.context)) {
                    var maxAge: Int = 0
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public,max-age=" + maxAge)
                            .removeHeader("School")
                            .build()
                } else {
                    var maxStale: Int = 3600 * 24 * 7
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("School")
                            .build()
                }


            }
            builder.cache(cache).addNetworkInterceptor(cacheInterceptor)

            /**
             *  公共参数，代码略
             */
            var addQueryParameterInterceptor: Interceptor = Interceptor { chain ->
                var request = chain.request()
                var method: String = request.method()
                var headers: Headers = request.headers()
                var modifiedUrl = request.url().newBuilder()
                        .addQueryParameter("platform", "android")
                        .addQueryParameter("version", "1.0.0")
                        .build()
                chain.proceed(request.newBuilder().url(modifiedUrl).build())

            }
            builder.addInterceptor(addQueryParameterInterceptor)

            /**
             * 设置头，代码略
             */
            val headerInterceptor = Interceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()
                        //                            .header("AppType", "TPOS")
                        .header("Content-Type", "application/json")
//                        .header("Accept", "application/json")
                        .method(originalRequest.method(), originalRequest.body())
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            //设置头
            builder.addInterceptor(headerInterceptor)

            /**
             * 设置cookie，代码略
             */

            /**
             * 设置超时和重连，代码略
             */
            //设置超时
            builder.connectTimeout(30, TimeUnit.SECONDS)
            builder.readTimeout(20, TimeUnit.SECONDS)
            builder.writeTimeout(20, TimeUnit.SECONDS)
            //错误重连
            builder.retryOnConnectionFailure(true)


            //以上设置结束，才能build(),不然设置白搭
            val okHttpClient = builder.build()
            mRetrofit = Retrofit.Builder()
                    .baseUrl(SERVER_HOST)
                    //设置 Json 转换器
                    .addConverterFactory(GsonConverterFactory.create())
                    //RxJava 适配器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()

        }
        return mRetrofit!!
    }

    val mApi: Api
        get() = retrofit().create(Api::class.java)

    companion object {
        val SERVER_HOST: String = "http://192.168.1.93/"
    }

    /**
     * 判断当前网络是否可用

     * @return
     */
    private fun isNetWorkAvailable(context: Context): Boolean {
        val manager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (manager != null) {
            val info = manager.activeNetworkInfo
            info != null && info.isConnected
        } else {
            false
        }

    }
}