package com.example.a1weather.presentation

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.commit
import com.example.a1weather.R
import com.example.a1weather.helper.Constants
import com.example.a1weather.presentation.fragments.HomeFragment
import com.example.a1weather.presentation.fragments.SplashFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment()
    }

    private fun addFragment() {
        supportFragmentManager.commit {
            add(R.id.containerView, SplashFragment::class.java, null)
            addToBackStack(Constants.SPLASH_FRAGMENT)
            setReorderingAllowed(true)
        }
    }

}