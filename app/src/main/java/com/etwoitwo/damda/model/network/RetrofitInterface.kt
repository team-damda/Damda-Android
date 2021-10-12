package com.etwoitwo.damda.model.network


import com.etwoitwo.damda.model.dataclass.CommonStatusData
import com.etwoitwo.damda.model.dataclass.StockData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("common/status")
    fun requestCommonStatus(@Query("UserId") UserId: Int): Call<CommonStatusData>

    @GET("main/interestStocks")
    fun requestMainInterest(@Query("UserId") UserId: Int): Call<StockData>

    @GET("common/holdingStocks")
    fun requestCommonHolding(@Query("UserId") UserId: Int): Call<StockData>

    @GET("wallet/transactions")
    fun requestWalletTransactions(@Query("UserId") UserId: Int): Call<StockData>
}