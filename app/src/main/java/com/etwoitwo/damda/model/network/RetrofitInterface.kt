package com.etwoitwo.damda.model.network


import com.etwoitwo.damda.model.dataclass.CommonStatusData
import com.etwoitwo.damda.model.dataclass.StockData
import com.etwoitwo.damda.model.dataclass.SuccessData
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {
    @GET("common/status")
    fun requestCommonStatus(@Query("UserId") UserId: Int): Call<CommonStatusData>

    @GET("main/interestStocks")
    fun requestMainInterest(@Query("UserId") UserId: Int): Call<StockData>

    @GET("common/holdingStocks")
    fun requestCommonHolding(@Query("UserId") UserId: Int): Call<StockData>

    @GET("wallet/transactions")
    fun requestWalletTransactions(@Query("UserId") UserId: Int): Call<StockData>

    @POST("graph/buying")
    fun requestGraphBuying(@Body params:HashMap<String, String>): Call<SuccessData>
}