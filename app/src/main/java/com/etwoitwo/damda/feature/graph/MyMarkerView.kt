package com.etwoitwo.damda.feature.graph

import android.content.Context
import android.widget.TextView
import com.etwoitwo.damda.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight

class MyMarkerView : MarkerView {
    private lateinit var tvContent: TextView // marker
    constructor(context: Context?, layoutResource: Int) : super(context, layoutResource) {
//        tvContent = findViewById(R.id.test_marker_view)
    } // draw override를 사용해 marker의 위치 조정 (bar의 상단 중앙)

    // entry를 content의 텍스트에 지정
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        tvContent.text = e?.y.toString()
        super.refreshContent(e, highlight)
    }
}