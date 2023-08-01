package com.jonkoit.repository

import com.jonkoit.dataclasessandmodels.ForecastData
import com.jonkoit.dataclasessandmodels.ForecastResponse
import com.jonkoit.dataclasessandmodels.WeatherResponse
import com.jonkoit.datasource.WeatherDataSource
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherDataSource: WeatherDataSource) {

    // Caching data to avoid making redundant API calls
    private var cachedCurrentWeather: WeatherResponse? = null
    private var cachedForecast: ForecastResponse? = null

    suspend fun getCurrentWeather(location: String): WeatherResponse? {
        // Check if cached data is available
        if (cachedCurrentWeather != null) {
            return cachedCurrentWeather!!
        }

        // If cached data is not available, make the API call
        val currentWeather = weatherDataSource.getCurrentWeather(location)

        // Cache the result
        cachedCurrentWeather = currentWeather

        return currentWeather
    }

    suspend fun getForecast(location: String): ForecastResponse? {
        // Check if cached data is available
        if (cachedForecast != null) {
            return cachedForecast!!
        }

        // If cached data is not available, make the API call
        val forecast = weatherDataSource.getForecast(location)

        // Cache the result
        cachedForecast = forecast

        return forecast
    }

    suspend fun getFiveDayForecast(cityName: String): List<ForecastData> {
        // Make the API call to get 5-day forecast data
        return weatherDataSource.getFiveDayForecast(cityName)
    }
}