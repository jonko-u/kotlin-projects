package com.jonkoit.datasource

import com.jonkoit.apiservice.WeatherApiService
import com.jonkoit.dataclasessandmodels.ForecastData
import com.jonkoit.dataclasessandmodels.ForecastResponse
import com.jonkoit.dataclasessandmodels.WeatherData
import com.jonkoit.dataclasessandmodels.WeatherResponse

class WeatherApiDataSource(private val weatherApiService: WeatherApiService) : WeatherDataSource {

    // ... other functions ...

    override suspend fun getCurrentWeather(location: String): WeatherResponse {
        // Get the latitude and longitude from the GeoApi
        val latLng = GeoApiClient.getLatLng(location)

        if (latLng != null) {
            val (latitude, longitude) = latLng
            // Call the WeatherApiService's getCurrentWeather function with the required parameters
            return weatherApiService.getCurrentWeather(
                apiKey = "your_api_key_here",
                latitude = latitude,
                longitude = longitude,
                exclude = "minutely,hourly,daily,alerts"
            )
        } else {
            // If location data is not available, throw an exception or handle the error as needed.
            throw Exception("Location data not available")
        }
    }

    override suspend fun getForecast(location: String): ForecastResponse {
        // Get the latitude and longitude from the GeoApi
        val latLng = GeoApiClient.getLatLng(location)

        if (latLng != null) {
            val (latitude, longitude) = latLng
            // Call the WeatherApiService's getForecast function with the required parameters
            return weatherApiService.getForecast(
                apiKey = "your_api_key_here",
                latitude = latitude,
                longitude = longitude,
                exclude = "current,hourly,alerts"
            )
        } else {
            // If location data is not available, throw an exception or handle the error as needed.
            throw Exception("Location data not available")
        }
    }

override suspend fun getFiveDayForecast(cityName: String): List<ForecastData> {
        TODO("Not yet implemented")
    }

    // ... other functions ...
}


//class WeatherApiDataSource (private val weatherApiService: WeatherApiService) : WeatherDataSource {
//
//    // Default constructor without parameters
//
//    override suspend fun getCurrentWeather(cityName: String): WeatherResponse {
//        return weatherApiService.getCurrentWeather(cityName)
//    }
//
//    override suspend fun getForecast(cityName: String): ForecastResponse {
//        return weatherApiService.getForecast(cityName)
//    }
//
//    override suspend fun getFiveDayForecast(cityName: String): List<ForecastData> {
//        return weatherApiService.getFiveDayForecast(cityName)
//    }
//}