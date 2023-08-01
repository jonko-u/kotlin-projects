package com.jonkoit.datasource

import com.jonkoit.dataclasessandmodels.ForecastData
import com.jonkoit.dataclasessandmodels.ForecastResponse
import com.jonkoit.dataclasessandmodels.GeoResponse
import com.jonkoit.dataclasessandmodels.WeatherResponse

interface GeoDataSource {
    suspend fun getLat(location: String): GeoResponse
}