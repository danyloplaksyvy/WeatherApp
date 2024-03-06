package com.example.weatherapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofitWeather =
    Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com")
        .addConverterFactory(GsonConverterFactory.create()).build()

val forecastService = retrofitWeather.create(ForecastService::class.java)


interface ForecastService {
    @GET("/v1/forecast.json")
    suspend fun getForecast(
        @Query("key") key: String = "984df67f8d104a3996a152616242702",
        @Query("q") q: String,
        @Query("days") days: Int = 7,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): ForecastResponse
}

// https://api.weatherapi.com
// /v1/forecast.json
// ?key=984df67f8d104a3996a152616242702&q=Kyiv&days=3&aqi=no&alerts=no

