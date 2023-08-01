package com.jonkoit.datasource

import com.jonkoit.apiservice.GeoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GeoApiClient {
    private const val BASE_URL = "https://maps.googleapis.com/maps/api/geocode/"
    private const val API_KEY = "YOUR_GOOGLE_MAPS_API_KEY"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val geoService: GeoService by lazy {
        retrofit.create(GeoService::class.java)
    }

    suspend fun getLatLng(locationName: String): Pair<Double, Double>? {
        val response = geoService.getLatLng(locationName, API_KEY)
        if (response.isSuccessful) {
            val results = response.body()?.results
            if (!results.isNullOrEmpty()) {
                val location = results[0].geometry?.location
                if (location != null) {
                    return location.lat to location.lng
                }
                return null
            }
        }
        return null
    }
}