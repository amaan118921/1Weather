package com.example.a1weather.data.network

import com.example.a1weather.data.models.WeatherResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather/")
    suspend fun getWeatherDetails(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("appid") apiKey: String): Response<WeatherResponseModel>

    @GET("weather/")
    suspend fun getWeatherByCity(@Query("q") city: String, @Query("appid") apiKey: String): Response<WeatherResponseModel>
}