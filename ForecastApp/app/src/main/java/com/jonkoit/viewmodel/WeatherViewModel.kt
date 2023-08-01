package com.jonkoit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonkoit.dataclasessandmodels.ForecastResponse
import com.jonkoit.dataclasessandmodels.WeatherResponse
import com.jonkoit.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _currentWeather = MutableLiveData<WeatherResponse>()
    val currentWeather: LiveData<WeatherResponse>
        get() = _currentWeather

    private val _forecast = MutableLiveData<ForecastResponse>()
    val forecast: LiveData<ForecastResponse>
        get() = _forecast

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun getCurrentWeather(location: String) {
        viewModelScope.launch {
            try {
                val weatherResponse = weatherRepository.getCurrentWeather(location)
                _currentWeather.value = weatherResponse
            } catch (e: Exception) {
                _error.value = "Failed to fetch current weather data"
            }
        }
    }

    fun getForecast(location: String) {
        viewModelScope.launch {
            try {
                val forecastResponse = weatherRepository.getForecast(location)
                _forecast.value = forecastResponse
            } catch (e: Exception) {
                _error.value = "Failed to fetch forecast data"
            }
        }
    }
}