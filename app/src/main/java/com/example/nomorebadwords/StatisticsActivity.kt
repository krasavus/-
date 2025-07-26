package com.example.nomorebadwords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class StatisticsActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        barChart = findViewById(R.id.barChart)

        loadStatistics()
    }

    private fun loadStatistics() {
        val sharedPreferences = getSharedPreferences("com.example.nomorebadwords", MODE_PRIVATE)
        val entries = ArrayList<BarEntry>()
        for (i in 1..7) {
            val count = sharedPreferences.getInt("swearing_day_$i", 0)
            entries.add(BarEntry(i.toFloat(), count.toFloat()))
        }

        val dataSet = BarDataSet(entries, "Срывы по дням недели")
        val barData = BarData(dataSet)
        barChart.data = barData
        barChart.invalidate()
    }
}
