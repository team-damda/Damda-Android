package com.etwoitwo.damda.feature.graph

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.etwoitwo.damda.databinding.FragmentGraph1Binding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.util.Random

class ChartFragment1 : Fragment() {
    private var _binding: FragmentGraph1Binding? = null
    private val binding get() = _binding!!

    private lateinit var lineChart: LineChart
    private var stockList = ArrayList<Stock>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGraph1Binding.inflate(inflater, container, false)
        lineChart = binding.lineChart
        initLineChart()
        setDataToLineChart()
        return binding.root
    }
    private fun initLineChart() {

//        hide grid lines
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

    }


    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if (index < stockList.size) {
                stockList[index].date
            } else {
                ""
            }
        }
    }

    private fun setDataToLineChart() {
        //now draw bar chart with dynamic data
        val entries: ArrayList<Entry> = ArrayList()

        stockList = getstockList()

        //you can replace this data object with  your custom object
        for (i in stockList.indices) {
            val Stock = stockList[i]
            entries.add(Entry(i.toFloat(), Stock.money.toFloat()))
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
    private fun getstockList(): ArrayList<Stock> {
        var date = 0
        var money = 24000
        for (i: Int in 1..50){
            stockList.add(Stock("21.10.14 11:"+date.toString(), money+rand(0, 10000)))
            date = date + 1
        }

        return stockList
    }
    fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }

}