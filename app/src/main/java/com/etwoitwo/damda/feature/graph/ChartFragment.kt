package com.etwoitwo.damda.feature.graph

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.etwoitwo.damda.R
import com.etwoitwo.damda.databinding.FragmentGraphBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.math.log

class ChartFragment : Fragment() {
    private var _binding: FragmentGraphBinding? = null
    private val binding get() = _binding!!

    private lateinit var lineChart: LineChart
    private var stockList = ArrayList<Stock>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        lineChart = binding.lineChart
        setLineChart()
        setDataToLineChart()
        return binding.root
    }
    private fun setLineChart() {
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.axisRight.setDrawGridLines(false)
        lineChart.isHighlightPerTapEnabled = true
        lineChart.axisRight.setDrawLabels(false)
        lineChart.axisRight.isEnabled = false
        lineChart.axisLeft.setDrawLabels(false)
        lineChart.axisLeft.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.setScaleEnabled(false)
        lineChart.setPinchZoom(false)
        lineChart.setDrawMarkers(true)
        lineChart.setTouchEnabled(true)

        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove description label
        lineChart.description.isEnabled = false

        //add animation
        lineChart.animateX(1000)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(false)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f

        // marker 설정
        val marker = MyMarkerView(context, layoutResource = R.layout.item_marker_view)
        lineChart.marker = marker
    }


    inner class MyAxisFormatter : IndexAxisValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            var valueToMinutes = TimeUnit.MINUTES.toMillis(value.toLong())
            var timeMinutes = Date(valueToMinutes)
            var formatMinutes = SimpleDateFormat("HH:mm")

            return formatMinutes.format(timeMinutes)
        }
    }

    private fun setDataToLineChart() {
        //now draw bar chart with dynamic data
        val entries: ArrayList<Entry> = ArrayList()
        //you can replace this data object with  your custom object
        stockList = getStockList()
        for (i in stockList.indices) {
            val stock = stockList[i]
            entries.add(Entry(stock.timestamp.toFloat(), stock.money.toFloat()))
        }

        val dataSet = LineDataSet(entries, "")

        val data = LineData(dataSet)
        lineChart.data = data

        dataSet.setDrawHorizontalHighlightIndicator(false)
        dataSet.color = Color.parseColor("#e67c49")
        dataSet.setDrawCircles(false)
        dataSet.setDrawValues(false)
        dataSet.lineWidth = 3f

        lineChart.invalidate()
    }

    // simulate api call
    // we are initialising it directly
    val random = Random()
    val num = random.nextInt(100)
    private fun getStockList(): ArrayList<Stock> {
        var time = 1648102448
        var money = 24000
        for (i: Int in 1..100){
            stockList.add(Stock(time , money))
            time += 60
            money += 10
        }

        return stockList
    }
    fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }

}