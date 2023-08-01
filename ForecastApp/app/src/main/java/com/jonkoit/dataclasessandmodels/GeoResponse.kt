package com.jonkoit.dataclasessandmodels

data class GeoResponse(
    val results: List<GeoResult>
)

data class GeoResult(
    val geometry: GeoGeometry?
)

data class GeoGeometry(
    val location: GeoLocation?
)

data class GeoLocation(
    val lat: Double,
    val lng: Double
)