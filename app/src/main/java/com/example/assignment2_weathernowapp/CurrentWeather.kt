package com.example.assignment2_weathernowapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlin.random.Random


class CurrentWeather : Fragment() {
    private lateinit var locationSpinner: Spinner
    private lateinit var temperatureUnitRadioGroup: RadioGroup

    // Temperature and condition textviews
    private lateinit var temperatureTextView: TextView
    private lateinit var conditionTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_current_weather, container, false)

        // Initialize temperature and condition textviews
        temperatureTextView = view.findViewById(R.id.temperature_textview)
        conditionTextView = view.findViewById(R.id.weather_condition_textview)

        var currentWeather : Int =  generateTemp()
        generateWeatherCondition()


        // Set up the location spinner
        locationSpinner = view.findViewById(R.id.location_spinner)
        val locations = arrayOf("Amman", "As-Salt", "Madaba", "Irbid", "Kerak", "Jerash")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, locations)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        locationSpinner.adapter = adapter

        locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Generate new random temperature value and update the text view
                currentWeather = generateTemp()
                generateWeatherCondition()
                // Reset the radio group to default Fahrenheit value
                temperatureUnitRadioGroup.check(R.id.fahrenheit_radio_button)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        // Set up the temperature unit radio buttons
        temperatureUnitRadioGroup = view.findViewById(R.id.temperature_unit_radio_group)
        // Set default radio button to Fahrenheit
        temperatureUnitRadioGroup.check(R.id.fahrenheit_radio_button)
        temperatureUnitRadioGroup.setOnCheckedChangeListener { _, checkedId ->

            when (checkedId) {
                R.id.celsius_radio_button -> {
                    // Convert temperature to Celsius
                    val celsiusTemp = (currentWeather - 32) * 5 / 9
                    currentWeather = celsiusTemp

                    // Update temperature text view with Celsius temperature
                    temperatureTextView.text = "$celsiusTemp°C"

                }
                R.id.fahrenheit_radio_button -> {
                    // Convert temperature to Fahrenheit
                    val fahrenheitTemp = currentWeather * 9 / 5 + 32
                    currentWeather = fahrenheitTemp

                    // Update temperature text view with Fahrenheit temperature
                    temperatureTextView.text = "$fahrenheitTemp°F"
                }
            }
        }

        // Set up the refresh button
        val refreshButton = view.findViewById<Button>(R.id.refresh_button)
        refreshButton.setOnClickListener {
            currentWeather = generateTemp()
            generateWeatherCondition()
        }

        return view
    }

    fun generateTemp(): Int
    {
        // Generate fake random value for temperature
        val temperature = Random.nextInt(0, 101)

        // Set the fake random values to the textview
        temperatureTextView.text = "$temperature °F"
        return  temperature
    }


    fun generateWeatherCondition(): String
    {
        // Generate fake random value for condition

        val conditions = listOf(
            "Sunny",
            "Rainy",
            "Cloudy",
            "Snowy"
        )
        val condition = conditions[Random.nextInt(conditions.size)]
        conditionTextView.text = condition
        return  condition
    }
}