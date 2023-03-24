package com.example.a1weather.presentation.application

import android.app.Application
import com.example.a1weather.helper.HelpRepo
import com.example.a1weather.room.WeatherDatabase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication @Inject constructor(): Application() {
    @Inject
    lateinit var repo: HelpRepo

    override fun onCreate() {
        super.onCreate()
        repo.initSharedPreferences()
    }
}