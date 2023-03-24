package com.example.a1weather.data.repositories

import com.example.a1weather.data.models.WeatherResponseModel
import com.example.a1weather.data.network.ApiService
import com.example.a1weather.domain.repositories.AppRepository
import retrofit2.Response
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private val apiService: ApiService) : AppRepository {
    override suspend fun getWeatherDetails(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Response<WeatherResponseModel> {
        return apiService.getWeatherDetails(lat, lon, apiKey)
    }

    override suspend fun getWeatherByCity(
        city: String,
        apiKey: String
    ): Response<WeatherResponseModel> {
        return apiService.getWeatherByCity(city, apiKey)
    }
}