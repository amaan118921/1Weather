package com.example.a1weather.domain.repositories

import com.example.a1weather.data.models.WeatherResponseModel
import com.example.a1weather.domain.models.WeatherResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AppRepository {
    suspend fun getWeatherDetails(lat: Double, lon: Double, apiKey: String): Response<WeatherResponseModel>
    suspend fun getWeatherByCity(city: String, apiKey: String): Response<WeatherResponseModel>
}