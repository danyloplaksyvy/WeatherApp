package com.example.weatherapp

// Objects for navigation
sealed class Screen(val route: String) {
    object MainScreen:Screen("mainscreen")
    object CityListScreen:Screen("citylistscreen")
}