package com.etwoitwo.damda.model.dataclass

data class MainStatusData(
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

