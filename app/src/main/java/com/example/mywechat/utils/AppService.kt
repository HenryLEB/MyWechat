package com.example.retrofittest

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppService {

    @GET("get_data.json")
    fun getAppData(): Call<List<App>>

//    @GET("get_data.json")
//    fun getData(@Query("u") user: String,@Query("t") token: String): Call<Data>
//
//    @GET("{page}/get_data.json")
//    fun getDataByPage(@Path("page") page: Int): Call<Data>
//
//    @DELETE("data/{id}")
//    fun deleteData(@Path("id") id:String): Call<ResponseBody>

}