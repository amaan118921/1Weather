package com.example.a1weather.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.a1weather.R
import com.example.a1weather.domain.models.WeatherResponse
import com.example.a1weather.helper.*
import com.example.a1weather.presentation.MainActivity
import com.example.a1weather.presentation.adapters.HomeAdapter
import com.example.a1weather.presentation.viewModel.AppViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.permission_not_granted.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : BaseFragment(), PermissionUtils.IPermission, OnRefreshListener {

    private val viewModel: AppViewModel by activityViewModels()

    private val permissionUtils = PermissionUtils(this)
    private var lat: Double? = null
    private var lon: Double? = null
    private var adapter: HomeAdapter? = null
    private var cityList: ArrayList<WeatherResponse>? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val permissionList = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    @Inject
    lateinit var repo: HelpRepo

    override fun getLayoutRes(): Int {
        return R.layout.fragment_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            finish()
        }
        cityList = arrayListOf()
        checkForPermission()
        viewModel.getCityWeathers()
        val city = getCurrentCity()
        viewModel.getCachedWeatherDetails(city)
        getCityWeathers()
        initRecyclerView()
        refreshLayout.setOnRefreshListener(this)
        btnGivePermission.setOnClickListener {
            toAppSettings()
        }
        setObservers()
    }

    private fun toAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", Constants.PACKAGE_NAME, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun getCityWeathers() {
        cityList?.clear()
        viewModel.getCachedByCity(Constants.SYDNEY)
        viewModel.getCachedByCity(Constants.DELHI)
        viewModel.getCachedByCity(Constants.MUMBAI)
        viewModel.getCachedByCity(Constants.SINGAPORE)
        viewModel.getCachedByCity(Constants.NEW_YORK)
        viewModel.getCachedByCity(Constants.MELBOURNE)
    }

    private fun initRecyclerView() {
        adapter = HomeAdapter()
        rvHome.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvHome.adapter = adapter
        submitList()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun submitList() {
        adapter?.submitList(cityList)
        adapter?.notifyDataSetChanged()
    }

    fun set() {
        viewModel.cityWeather.observe(viewLifecycleOwner) {
            updateUI(it)
            hideProgressFrame()
        }
    }

    private fun setObservers() {
        viewModel.city.observe(viewLifecycleOwner) {
            updateCurrentCity(it)
            val city = getCurrentCity()
            viewModel.getCachedWeatherDetails(city)
            set()
        }
        viewModel.cityWeather.observe(viewLifecycleOwner) {
            updateUI(it)
            hideProgressFrame()
        }
        viewModel.melbourneWeather.observe(viewLifecycleOwner) {
            it?.let { it1 -> if(cityList?.contains(it1)==false) cityList?.add(it1)  }
            submitList()
        }

        viewModel.sydneyWeather.observe(viewLifecycleOwner) {
            it?.let { it1 -> if(cityList?.contains(it1)==false) cityList?.add(it1)  }
            submitList()
        }

        viewModel.mumbaiWeather.observe(viewLifecycleOwner) {
            it?.let { it1 -> if(cityList?.contains(it1)==false) cityList?.add(it1)  }
            submitList()
        }

        viewModel.delhiWeather.observe(viewLifecycleOwner) {
            it?.let { it1 -> if(cityList?.contains(it1)==false) cityList?.add(it1)  }
            submitList()
        }

        viewModel.newYorkWeather.observe(viewLifecycleOwner) {
            it?.let { it1 -> if(cityList?.contains(it1)==false) cityList?.add(it1)  }
            submitList()
        }

        viewModel.singaporeWeather.observe(viewLifecycleOwner) {
            it?.let { it1 -> if(cityList?.contains(it1)==false) cityList?.add(it1)  }
            submitList()
        }
    }

    private fun updateCurrentCity(it: String?) {
        it?.let { it1 -> repo.setSharedPreferences(Constants.CURRENT_CITY, it1) }
    }

    private fun getCurrentCity(): String  {
        return repo.getSharedPreferences(Constants.CURRENT_CITY)
    }

    private fun hideProgressFrame() {
        pfHome?.makeGone()
        cl?.makeVisible()
    }

    private fun updateUI(weatherResponse: WeatherResponse?) {
        weatherResponse?.apply {
            tvDate.text = getCurrentDate()
            tvCity.text = "$name, $country"
            tvWeather.text = weatherMain
            tvTemp.text = "${((feelsLike ?: 273.15) - 273.15).roundToInt()}°C"
            tvWindValue.text = "${speed?.roundToInt()}km/h"
            tvHumidityValue.text = "${humidity}%"
            tvRainValue.text = "${rainValue?.roundToInt()}cm"
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("EEE, MMM d")
        return sdf.format(Calendar.getInstance().time)
    }

    private fun getDrawable(main: String?): Int {
        return when (main) {
            Constants.RAINY -> R.drawable.cludy
            Constants.CLEAR -> R.drawable.cludy
            Constants.CLOUDY -> R.drawable.cludy
            else -> R.drawable.cludy
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (permissionUtils.isPermissionGranted(permissionList, requireContext())) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                lat = location?.latitude
                lon = location?.longitude
                fetchData()
            }
        }
    }

    private fun checkForPermission() {
        if (permissionUtils.isPermissionGranted(permissionList, requireContext())) {
            hidePermission()
            fetchData()
        } else requestPermission()
    }

    private fun hidePermission() {
        layoutPNG?.makeGone()
    }

    private fun requestPermission() {
        requestPermissions(permissionList, Constants.LOCATION_CODE)
    }

    private fun fetchData() {
        if (lat != null && lon != null) {
            hideProgressFrame()
            viewModel.getWeatherDetails(lat!!, lon!!, Constants.API_KEY)
        } else {
            getCurrentLocation()
        }
    }


    private fun showProgressFrame() {
        pfHome?.makeVisible()
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionUtils.processPermissions(requestCode, permissions, grantResults)
    }

    override fun onPermissionGranted() {
        hidePermission()
        fetchData()
        showToast("Permission Granted", Toast.LENGTH_SHORT)
    }


    override fun onPermissionDenied() {
        requestPermission()
    }

    private fun showPermissionLayout() {
        layoutPNG?.makeVisible()
    }

    override fun onPermissionDeniedPermanently() {
        showToast("Permission denied", Toast.LENGTH_SHORT)
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing=false
        viewModel.getCityWeathers()
        if(permissionUtils.isPermissionGranted(permissionList, requireContext())) {
            hidePermission()
            fetchData()
        }
    }

}


