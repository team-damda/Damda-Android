package com.etwoitwo.damda.model.network


import com.etwoitwo.damda.model.dataclass.MainStatusData
import com.etwoitwo.damda.model.dataclass.StockData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("main/status")
    fun requestMainStatus(@Query("UserId") UserId: Int): Call<MainStatusData>

    @GET("main/interestStocks")
    fun requestMainInterest(@Query("UserId") UserId: Int): Call<StockData>

    @GET("common/holdingStocks")
    fun requestCommonHolding(@Query("UserId") UserId: Int): Call<StockData>
}