package com.example.a1weather.di.module

import android.content.Context
import androidx.room.Room
import com.example.a1weather.data.network.ApiService
import com.example.a1weather.data.repositories.AppRepositoryImpl
import com.example.a1weather.domain.repositories.AppRepository
import com.example.a1weather.helper.Constants
import com.example.a1weather.room.WeatherDao
import com.example.a1weather.room.WeatherDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(
            Constants.BASE_URL).build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppRepository(apiService: ApiService) : AppRepository {
        return AppRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext context: Context) : WeatherDatabase {
        var INSTANCE: WeatherDatabase? = null
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(context.applicationContext, WeatherDatabase::class.java, "weather_database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
            INSTANCE =instance
            return instance
        }
    }

    @Provides
    @Singleton
    fun provideWeatherDao(database: WeatherDatabase) : WeatherDao {
        return database.getDao()
    }

}