package com.example.a1weather.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.a1weather.data.models.*
import com.squareup.moshi.Json

@Entity(tableName = "weather_table")
data class WeatherResponse(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "lat")
    var lat: Double? = null,
    @ColumnInfo(name = "lon")
    var lon: Double? = null,
    @ColumnInfo(name = "weather_main")
    var weatherMain: String? = null,
    @ColumnInfo(name = "feels_like")
    var feelsLike: Double? = null,
    @ColumnInfo(name = "humidity")
    var humidity: Int? = null,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name="speed")
    var speed: Double? = null,
    @ColumnInfo(name="rain_value")
    var rainValue: Double? = null,
    @ColumnInfo(name="country")
    var country: String? = null
)
