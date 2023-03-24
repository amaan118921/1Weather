package com.example.a1weather.data.models

import com.example.a1weather.domain.models.WeatherResponse
import com.squareup.moshi.Json

data class WeatherResponseModel(
    @Json(name = "coord") var coOrdinates: Coordinates? = null,
    var weather: List<Weather>? = null,
    var base: String? = null,
    var main: TemperatureModel? = null,
    var visibility: Int? = null,
    var wind: WindModel? = null,
    var name: String? = null,
    var id: Int? = null,
    var timezone: Int? = null,
    var rain: RainModel? = null,
    @Json(name="sys") var countryModel: CountryModel? = null
)

fun WeatherResponseModel.toWeatherResponse(): WeatherResponse {
    return WeatherResponse().apply {
        weatherMain = this@toWeatherResponse.weather?.get(0)?.main
        lat = this@toWeatherResponse.coOrdinates?.lat
        lon = this@toWeatherResponse.coOrdinates?.lon
        feelsLike = this@toWeatherResponse.main?.feelsLike
        humidity = this@toWeatherResponse.main?.humidity
        name = this@toWeatherResponse.name
        country = this@toWeatherResponse.countryModel?.country
        speed = this@toWeatherResponse.wind?.speed
        rainValue = this@toWeatherResponse.rain?.rainValue
    }
}