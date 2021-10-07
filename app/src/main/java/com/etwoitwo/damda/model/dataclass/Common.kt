package com.etwoitwo.damda.model.dataclass

data class StockData(
    /* 메인 화면, 지갑 화면의 보유종목, 거래내역, 관심종목 부분의 각 아이템들 구성하는 데이터 형식*/
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ArrayList<Data>
    ) {
    data class Data(
        val marketType: String,
        val stockId: String,
        val stockName: String,
        val currentPrice: Int,

        /*[메인화면-관심종목] main/interestStocks */
        val todayChange: Int? = null,
        val todayRoC: Double? = null,

        /*[메인,지갑-보유종목] common/holdingStocks */
        val totCnt: Int? = null,
        val totProfitLoss: Int? = null,
        val totProfitLossRate: Double? = null,

        )
}
