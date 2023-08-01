package com.jonkoit.dataclasessandmodels

data class WeatherData(
    val temperature: Double,
    val humidity: Int,
    val windSpeed: Double,
    // Add more weather-related properties as needed
)
data class WeatherResponse(
    // Properties representing weather data returned by the API
    val temperature: Double,
    val humidity: Double,
    val windSpeed: Double,
    // Other properties...
)