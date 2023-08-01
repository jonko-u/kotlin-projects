package com.jonkoit.apiservice

import com.jonkoit.dataclasessandmodels.ForecastData
import com.jonkoit.dataclasessandmodels.ForecastResponse
import com.jonkoit.dataclasessandmodels.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    // Example of an endpoint to get current weather
    @GET("onecall")
    suspend fun getCurrentWeather(
        @Query("appid") apiKey: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String = "minutely,hourly,daily,alerts"
    ): WeatherResponse

    // Example of an endpoint to get forecast data
    @GET("onecall")
    suspend fun getForecast(
        @Query("appid") apiKey: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String = "current,hourly,alerts"
    ): ForecastResponse
}