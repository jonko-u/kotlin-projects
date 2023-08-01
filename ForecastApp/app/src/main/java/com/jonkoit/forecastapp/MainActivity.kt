package com.jonkoit.forecastapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jonkoit.dataclasessandmodels.ForecastResponse
import com.jonkoit.dataclasessandmodels.WeatherResponse
import com.jonkoit.forecastadapter.ForecastAdapter
import com.jonkoit.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // Inject WeatherViewModel using Hilt
    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Observe the LiveData for current weather updates
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        // Observe the LiveData for current weather updates
        viewModel.currentWeather.observe(this, { weatherResponse ->
            // Update the UI with the new current weather data
            updateUI(weatherResponse)
        })

        // Observe the LiveData for forecast updates
        viewModel.forecast.observe(this, { forecastResponse ->
            // Update the UI with the new forecast data
            updateForecastUI(forecastResponse)
        })

        // Observe the LiveData for error messages
        viewModel.error.observe(this, { errorMessage ->
            // Handle and display the error message to the user
            showError(errorMessage)
        })

        // Set up the search button click listener
        val searchButton = findViewById<Button>(R.id.btnSearch)
        val searchEditText = findViewById<EditText>(R.id.editTextSearch)

        searchButton.setOnClickListener {
            // Get the user input (location name) from the EditText
            val locationName = searchEditText.text.toString().trim()

            // Request weather data for the entered location
            viewModel.getCurrentWeather(locationName)
            viewModel.getForecast(locationName)
        }
    }

    private fun updateUI(weather: WeatherResponse) {
        // Update the UI with the received current weather data
        val temperatureTextView = findViewById<TextView>(R.id.textViewTemperature)
        val humidityTextView = findViewById<TextView>(R.id.textViewHumidity)
        val windSpeedTextView = findViewById<TextView>(R.id.textViewWindSpeed)

        temperatureTextView.text = "Temperature: ${weather.temperature} °C"
        humidityTextView.text = "Humidity: ${weather.humidity}%"
        windSpeedTextView.text = "Wind Speed: ${weather.windSpeed} m/s"
    }

    private fun updateForecastUI(forecastResponse: ForecastResponse) {
        // Get the forecast data list from the ForecastResponse
        val forecastDataList = forecastResponse.forecastList

        // Update the UI with the received forecast data
        // For example, update the RecyclerView with the 5-day forecast
        val recyclerViewForecast = findViewById<RecyclerView>(R.id.recyclerViewForecast)
        val forecastAdapter = ForecastAdapter(forecastDataList)
        recyclerViewForecast.layoutManager = LinearLayoutManager(this)
        recyclerViewForecast.adapter = forecastAdapter
    }

    private fun showError(errorMessage: String) {
        // Display the error message to the user
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
