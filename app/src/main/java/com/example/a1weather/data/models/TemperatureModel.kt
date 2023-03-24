package com.example.a1weather.data.models

import com.squareup.moshi.Json

data class TemperatureModel(
    var temp: Double? = null,
    @Json(name = "feels_like") var feelsLike: Double? = null,
    @Json(name = "temp_min") var tempMin: Double? = null,
    @Json(name = "temp_max") var tempMax: Double? = null,
    @Json(name ="pressure") var pressure: Int? = null,
    @Json(name ="humidity") var humidity: Int? = null,
    @Json(name = "sea_level") var seaLevel: Int? = null,
    @Json(name = "grnd_level") var grndLevel: Int? = null
)
