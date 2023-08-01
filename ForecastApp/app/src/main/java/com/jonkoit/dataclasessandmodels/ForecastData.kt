package com.jonkoit.dataclasessandmodels

data class ForecastData(
    val date: String,
    val temperature: Double,
    // Add more forecast-related properties as needed
)
data class ForecastResponse(
    // Properties representing forecast data returned by the API
    val forecastList: List<ForecastItem>,
    // Other properties...
)
data class ForecastItem(
    // Properties representing individual forecast items in the list
    val date: Long,
    val temperature: Double,
    val description: String,
    // Other properties...
)