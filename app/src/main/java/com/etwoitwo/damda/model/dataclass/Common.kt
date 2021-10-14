package com.etwoitwo.damda.model.dataclass

import com.google.android.material.timepicker.TimeFormat
import java.text.DateFormat

data class CommonStatusData(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: Data
){
    data class Data (
        val nickname: String,
        val history: String,
        val deposit: String,
        val containStockAsset: String,
    )
}

data class StockData(
    /* 메인 화면, 지갑 화면의 보유종목, 거래내역, 관심종목 부분의 각 아이템들 구성하는 데이터 형식*/
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ArrayList<Data>
    ) {
    data class Data(
        val viewType: Int = 0, // 0은 일반 stock list item 형식, 1은 보유 종목의 stock list day 형식

        /* 지갑화면-거래내역의 해당일 아이템 형식(layout_wallet_stocklistday 참고)*/
        val transDayDate: String? = null,
        val transDayProfitLoss: Int? = null,
        val transDayType: Int? = null, // 0이면 매수만 있는 날, 1이면 매도만 있는 날, 2면 매수매도 모두 있는 날

        /* 그외 아이템 형식 */
        val marketType: String ?= null,
        val stockId: String ?= null,
        val stockName: String ?= null,
        val currentPrice: Int ?= null,

        /*[메인화면-관심종목] main/interestStocks */
        val todayChange: Int? = null,
        val todayRoC: Double? = null,

        /*[메인,지갑-보유종목] common/holdingStocks */
        val totCnt: Int? = null,
        val totProfitLoss: Int? = null,
        val totProfitLossRate: Double? = null,

        /*[지갑화면-거래내역] wallet/transaction */
        val transType: String? = null,
        val transPrice: Int?= null,
        val transAmount: Int?= null,
        val transTime: String? = null,
        val transProfitLoss: Int?= null,
        val transProfitLossRate: Double?= null,

        )
}
