package com.example.weatherapp.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.*
import kotlinx.coroutines.launch

// Responsible for fetching data about weather
//Making an API call
class MainViewModel : ViewModel() {
    private val _forecastState = mutableStateOf(ForecastState())
    val forecastState: State<ForecastState> = _forecastState

    init {
        fetchWeather()
    }

    //Maybe suspend?
    private fun fetchWeather() {
        viewModelScope.launch {
            try {
                val responseForecast = forecastService.getForecast()
                _forecastState.value = _forecastState.value.copy(
                    loading = false,
                    location = responseForecast.location,
                    current = responseForecast.current,
                    forecast = responseForecast.forecast,
                    error = null
                )

                println("Данные о погоде успешно получены: $responseForecast")
            } catch (e: Exception) { /*NEED TO UPDATE*/
                _forecastState.value = _forecastState.value.copy(
                    loading = false,
                    error = "Error fetching forecast"
                )
                println("Данные о погоде успешно не получены: $e")
            }
        }
    }

    // Forecast
    data class ForecastState(
        val loading: Boolean = true,
        val error: String? = null,
        val location: Location? = null,
        val current: Current? = null,
        val forecast: Forecast? = null,
    )

}

