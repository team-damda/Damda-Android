package com.etwoitwo.damda

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("main/status")
    fun requestAllData(@Query("UserId") UserId: Int): Call<MainStatusData>
}