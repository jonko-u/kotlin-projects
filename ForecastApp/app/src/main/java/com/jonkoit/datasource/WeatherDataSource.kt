package com.jonkoit.datasource

import com.jonkoit.dataclasessandmodels.ForecastData
import com.jonkoit.dataclasessandmodels.ForecastResponse
import com.jonkoit.dataclasessandmodels.WeatherData
import com.jonkoit.dataclasessandmodels.WeatherResponse

interface WeatherDataSource {
    suspend fun getCurrentWeather(location: String): WeatherResponse
    suspend fun getForecast(location: String): ForecastResponse
    suspend fun getFiveDayForecast(cityName: String): List<ForecastData>
}