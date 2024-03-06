package com.example.weatherapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


//The weather part

@Parcelize
data class Location(
    val name: String,
    val country: String,
) : Parcelable

@Parcelize
data class Condition(
    val text: String,
    val icon: String
) : Parcelable

@Parcelize
data class Current(
    val temp_c: Double,
    val is_day: Int,
    val condition: Condition
) : Parcelable

// The forecast part
@Parcelize
data class Day(
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val avgtemp_c: Double,
    val condition: Condition
) : Parcelable

@Parcelize
data class ForecastDay(
    val date: String,
    val day: Day,
    val hour: List<Hour>
) : Parcelable

@Parcelize
data class Hour(
    val time: String,
    val temp_c: Double,
    val is_day: Int,
    val condition: Condition
) : Parcelable

@Parcelize
data class Forecast(
    val forecastday: List<ForecastDay>
) : Parcelable

data class ForecastResponse(
    // Weather part
    val location: Location,
    val current: Current,
    // Forecast part
    val forecast: Forecast
)


// Current Location
data class LocationData(
    val latitude: Double,
    val longitude: Double
)