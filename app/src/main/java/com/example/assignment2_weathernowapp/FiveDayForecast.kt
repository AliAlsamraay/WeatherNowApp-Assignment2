package com.example.assignment2_weathernowapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class FiveDayForecast : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_five_day_forecast, container, false)

        loadData(view)
        val refreshBtn: Button = view.findViewById(R.id.refresh_btn)

        refreshBtn.setOnClickListener {
            refreshData(view)
        }

        return view
    }


    private fun loadData(view : View) {
        val forecastLayout = view.findViewById<LinearLayout>(R.id.items_layout)
        val forecastData = generateForecastData()

        for (data in forecastData) {
            val forecastView = LayoutInflater.from(requireContext()).inflate(R.layout.forecast_item, forecastLayout, false)

            val dateTextView = forecastView.findViewById<TextView>(R.id.date_textview)
            val temperatureTextView : TextView = forecastView.findViewById<TextView>(R.id.temperature_textview)
            val conditionTextView = forecastView.findViewById<TextView>(R.id.condition_textview)

            dateTextView.text = data.date
            temperatureTextView.text = data.temperature.toString()
            conditionTextView.text = data.condition

            forecastLayout.addView(forecastView)
        }
    }

    private fun refreshData(view: View) {
        val forecastLayout = view.findViewById<LinearLayout>(R.id.items_layout)
        forecastLayout.removeAllViews()

        loadData(view)
    }


    private fun generateForecastData(): List<ForecastData> {
        val forecastData = mutableListOf<ForecastData>()

        for (i in 1..5) {
            val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDate.now().plusDays(i.toLong()).format(DateTimeFormatter.ofPattern("EEE, MMM d"))
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val temperature = Random.nextInt(50, 101)
            val conditions = listOf(
                "Sunny",
                "Rainy",
                "Cloudy",
                "Snowy"
            )
            val condition = conditions[Random.nextInt(conditions.size)]

            forecastData.add(ForecastData(date, temperature, condition))
        }

        return forecastData
    }
}

data class ForecastData(
    val date: String,
    val temperature: Int,
    val condition: String
)
