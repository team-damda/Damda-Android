package com.etwoitwo.damda.model.dataclass

data class Search (
    val marketType: String,
    val stockId: String,
    val stockName: String,
    val currentPrice: Int
)