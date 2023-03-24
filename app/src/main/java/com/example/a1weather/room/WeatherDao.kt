package com.example.a1weather.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.a1weather.domain.models.WeatherResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherResponse: WeatherResponse?)

    @Query("SELECT * FROM weather_table WHERE name=:city")
    fun getWeatherByCity(city: String): Flow<WeatherResponse>

}