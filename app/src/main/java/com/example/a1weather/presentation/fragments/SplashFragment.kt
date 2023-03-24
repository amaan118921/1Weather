package com.example.a1weather.presentation.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.commit
import com.example.a1weather.R
import com.example.a1weather.helper.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment: BaseFragment() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_splash
    }
    private lateinit var runnable: Runnable
    private var handler: Handler? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this ) {
            finish()
        }
        handler = Handler(Looper.myLooper()!!)
        runnable = Runnable {
            addFragment()
        }
        handler?.postDelayed(runnable, 1500)
    }

    private fun addFragment() {
        requireActivity().supportFragmentManager.commit {
            add(R.id.containerView, HomeFragment::class.java, null)
            addToBackStack(Constants.HOME_FRAGMENT)
            setReorderingAllowed(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler?.removeCallbacks(runnable)
    }
}