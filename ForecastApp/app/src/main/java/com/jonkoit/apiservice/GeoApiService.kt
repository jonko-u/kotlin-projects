package com.jonkoit.apiservice

import com.jonkoit.dataclasessandmodels.GeoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoService {
    @GET("json")
    suspend fun getLatLng(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): Response<GeoResponse>
}