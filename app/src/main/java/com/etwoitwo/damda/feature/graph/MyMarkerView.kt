package com.etwoitwo.damda.feature.graph

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.widget.TextView
import com.etwoitwo.damda.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MyMarkerView : MarkerView {
    private lateinit var markerDate: TextView
    private lateinit var markerMoney: TextView
    constructor(context: Context?, layoutResource: Int) : super(context, layoutResource) {
        markerDate = findViewById(R.id.marker_stock_date)
        markerMoney = findViewById(R.id.marker_stock_money)

    } // draw override를 사용해 marker의 위치 조정 (bar의 상단 중앙)

    override fun draw(canvas: Canvas?) {
        canvas!!.translate(-(width / 2).toFloat(), -height.toFloat() )
//        Log.d("marker", width.toString() + " " + height.toString())
        super.draw(canvas)
    }

    // entry를 content의 텍스트에 지정
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        markerDate.text = getDateTime(e?.x)
        markerMoney.text = e?.y?.toInt().toString()
        Log.d("marker", highlight.toString())
        super.refreshContent(e, highlight)
    }

    private fun getDateTime(s: Float?): String {
        try {
            val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
            val netDate = Date((s?.toLong() ?: 0) * 1000)
            simpleDateFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            return simpleDateFormat.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}