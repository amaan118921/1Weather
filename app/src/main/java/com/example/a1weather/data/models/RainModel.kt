package com.example.a1weather.data.models

import com.squareup.moshi.Json

data class RainModel(
    @Json(name = "1h") var rainValue: Double? = null
)
