package com.jonkoit.datasource

import com.google.gson.GsonBuilder
import com.jonkoit.apiservice.WeatherApiService
import com.jonkoit.dataclasessandmodels.ForecastResponse
import com.jonkoit.dataclasessandmodels.WeatherResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object WeatherApiClient {
        private const val BASE_URL = "https://api.openweathermap.org/data/3.0/"
        private const val API_KEY = "API_KEY_HERE"

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val weatherService: WeatherApiService by lazy {
            retrofit.create(WeatherApiService::class.java)
        }

        suspend fun getCurrentWeather(locationName: String): WeatherResponse? {
            val latLng = GeoApiClient.getLatLng(locationName)
            if (latLng != null) {
                val (lat, lon) = latLng
                return weatherService.getCurrentWeather(API_KEY, lat, lon)
            }
            return null
        }

        suspend fun getForecast(locationName: String): ForecastResponse? {
            val latLng = GeoApiClient.getLatLng(locationName)
            if (latLng != null) {
                val (lat, lon) = latLng
                return weatherService.getForecast(API_KEY, lat, lon)
            }
            return null
        }
    }