package com.etwoitwo.damda.feature.graph

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.etwoitwo.damda.databinding.ItemMarkerViewBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight


class MyMarkerView(
    context: Context,
    layout: Int,
    private val stockData: ArrayList<Stock>
) : MarkerView(context, layout) {

    private lateinit var binding: ItemMarkerViewBinding

    private var stockMoney: TextView? = null

    init {
        stockMoney = binding.markerStockMoney
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        try {
            stockMoney?.text = stockData.toString()
        } catch (e: IndexOutOfBoundsException) { }

        super.refreshContent(e, highlight)
    }
}