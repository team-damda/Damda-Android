package com.etwoitwo.damda.feature.graph

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.etwoitwo.damda.databinding.ItemMarkerViewBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight


class MyMarkerView : MarkerView {
    private var _binding: ItemMarkerViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var markerStockDate: TextView
    private lateinit var markerStockTime: TextView
    private lateinit var markerStockMoney: TextView

    constructor(context: Context?, layoutResource: Int) : super(context, layoutResource) {
        markerStockDate = binding.markerStockDate
        markerStockTime = binding.markerStockTime
        markerStockMoney = binding.markerStockMoney
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        markerStockDate.text = e?.y.toString()
        markerStockTime.text = e?.y.toString()
        markerStockMoney.text = e?.y.toString()
        super.refreshContent(e, highlight)
    }
}