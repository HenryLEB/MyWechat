package com.example.secondexperiment.utils

import com.example.retrofittest.AppService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object ServiceCreator {

//    private const val BASE_URL = "http://10.0.2.2/"
    private const val BASE_URL = "http://10.0.2.2:3333/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}



suspend fun getAppData() {
    try {
        val appList = ServiceCreator.create<AppService>().getAppData().await() // 这段代码想运行通过，需要将BASE_URL中的地址改成http://localhost/
        println(appList)
        // 对服务器响应的数据进行处理
    } catch (e: Exception) {
        // 对异常情况进行处理
        e.printStackTrace()
    }
}

suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (body != null) continuation.resume(body)
                else continuation.resumeWithException(RuntimeException("response body is null"))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}