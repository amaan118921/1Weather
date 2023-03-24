package com.example.a1weather.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a1weather.domain.models.WeatherResponse

@Database(entities = [WeatherResponse::class], exportSchema = false, version = 5)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun getDao(): WeatherDao
}