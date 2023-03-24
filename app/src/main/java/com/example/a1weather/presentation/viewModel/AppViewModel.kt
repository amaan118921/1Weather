package com.example.a1weather.presentation.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.a1weather.data.models.toWeatherResponse
import com.example.a1weather.domain.models.WeatherResponse
import com.example.a1weather.domain.repositories.AppRepository
import com.example.a1weather.helper.Constants
import com.example.a1weather.room.WeatherDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {

    private var _currentWeather = MutableLiveData<WeatherResponse>()
    var currentWeather: LiveData<WeatherResponse> = _currentWeather

    private var _newYorkWeather = MutableLiveData<WeatherResponse>()
    var newYorkWeather: LiveData<WeatherResponse> = _newYorkWeather

    private var _singaporeWeather = MutableLiveData<WeatherResponse>()
    var singaporeWeather: LiveData<WeatherResponse> = _singaporeWeather

    private var _mumbaiWeather = MutableLiveData<WeatherResponse>()
    var mumbaiWeather: LiveData<WeatherResponse> = _mumbaiWeather

    private var _delhiWeather = MutableLiveData<WeatherResponse>()
    var delhiWeather: LiveData<WeatherResponse> = _delhiWeather

    private var _sydneyWeather = MutableLiveData<WeatherResponse>()
    var sydneyWeather: LiveData<WeatherResponse> = _sydneyWeather

    private var _melbourneWeather = MutableLiveData<WeatherResponse>()
    var melbourneWeather: LiveData<WeatherResponse> = _melbourneWeather

    private var _city: MutableLiveData<String?> = MutableLiveData()
    var city: LiveData<String?> = _city

    lateinit var cityWeather: LiveData<WeatherResponse>

    @Inject
    lateinit var appRepository: AppRepository

    @Inject
    lateinit var dao: WeatherDao


    fun getCityWeathers() {
        getWeatherByCity(Constants.SYDNEY, Constants.API_KEY)
        getWeatherByCity(Constants.DELHI, Constants.API_KEY)
        getWeatherByCity(Constants.MUMBAI, Constants.API_KEY)
        getWeatherByCity(Constants.SINGAPORE, Constants.API_KEY)
        getWeatherByCity(Constants.NEW_YORK, Constants.API_KEY)
        getWeatherByCity(Constants.MELBOURNE, Constants.API_KEY)
    }

    fun getWeatherDetails(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            try {
                val data =
                    appRepository.getWeatherDetails(lat, lon, apiKey).body()?.toWeatherResponse()
                _city.value = data?.name
                Log.d("data", data?.name?:"")
                withContext(Dispatchers.IO) {
                    dao.insertWeatherData(data)
                }
            } catch (e: Exception) {
                Log.d("internet", "no internet")
            }
        }
    }

    fun getCachedWeatherDetails(city: String) {
        viewModelScope.launch {
            try {
                cityWeather = dao.getWeatherByCity(city).asLiveData()
            }
            catch (e:Exception) {
                Log.d("room", e.localizedMessage)
            }
        }
    }

    fun getCachedByCity(city: String) {
        when (city) {
            Constants.NEW_YORK -> {
                viewModelScope.launch {
                    try {
                        newYorkWeather = dao.getWeatherByCity(city).asLiveData()
                    } catch (e: Exception) {
                    }
                }
            }
            Constants.SYDNEY -> {
                viewModelScope.launch {
                    try {
                        sydneyWeather =
                            dao.getWeatherByCity(city).asLiveData()
                    } catch (e: Exception) {
                    }

                }
            }
            Constants.SINGAPORE -> {
                viewModelScope.launch {
                    try {
                        singaporeWeather =
                            dao.getWeatherByCity(city).asLiveData()
                    } catch (e: Exception) {
                    }
                }
            }
            Constants.MELBOURNE -> {
                viewModelScope.launch {
                    try {
                        melbourneWeather =
                            dao.getWeatherByCity(city).asLiveData()
                    } catch (e: Exception) {
                    }
                }
            }
            Constants.DELHI -> {
                viewModelScope.launch {
                    try {
                        delhiWeather =
                            dao.getWeatherByCity(city).asLiveData()
                    } catch (e: Exception) {
                    }
                }
            }
            Constants.MUMBAI -> {
                viewModelScope.launch {
                    try {
                        mumbaiWeather=
                            dao.getWeatherByCity(city).asLiveData()
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

    private fun getWeatherByCity(city: String, apiKey: String) {
        when (city) {
            Constants.NEW_YORK -> {
                viewModelScope.launch {
                    try {
                        val data =
                        appRepository.getWeatherByCity(city, apiKey).body()?.toWeatherResponse()
                        withContext(Dispatchers.IO) {
                            dao.insertWeatherData(data)
                        }
                    }
                    catch (e: Exception) {
                        Log.d("internet", "no internet")
                    }
                }
            }
            Constants.MUMBAI -> {
                viewModelScope.launch {
                    try {
                        val data =
                            appRepository.getWeatherByCity(city, apiKey).body()?.toWeatherResponse()
                        withContext(Dispatchers.IO) {
                            dao.insertWeatherData(data)
                        }
                    }catch (e: Exception) {
                        Log.d("internet", "no internet")
                    }
                }
            }
            Constants.MELBOURNE -> {
                viewModelScope.launch {
                    try {
                         val data =
                            appRepository.getWeatherByCity(city, apiKey).body()?.toWeatherResponse()
                        withContext(Dispatchers.IO) {
                            dao.insertWeatherData(data)
                        }
                    }catch (e: Exception) {
                        Log.d("internet", "no internet")
                    }
                }
            }
            Constants.DELHI -> {
                viewModelScope.launch {
                    try {
                        val data =
                            appRepository.getWeatherByCity(city, apiKey).body()?.toWeatherResponse()
                        withContext(Dispatchers.IO) {
                            dao.insertWeatherData(data)
                        }
                    }catch (e: Exception) {
                        Log.d("internet", "no internet")
                    }
                }
            }
            Constants.SYDNEY -> {
                viewModelScope.launch {
                    try {
                        val data =
                            appRepository.getWeatherByCity(city, apiKey).body()?.toWeatherResponse()
                        withContext(Dispatchers.IO) {
                            dao.insertWeatherData(data)
                        }
                    }catch (e: Exception) {
                        Log.d("internet", "no internet")
                    }
                }
            }
            Constants.SINGAPORE -> {
                viewModelScope.launch {
                    try {
                       val data =
                            appRepository.getWeatherByCity(city, apiKey).body()?.toWeatherResponse()
                        withContext(Dispatchers.IO) {
                            dao.insertWeatherData(data)
                        }
                    }catch (e: Exception) {
                        Log.d("internet", "no internet")
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel(null)
    }
}
