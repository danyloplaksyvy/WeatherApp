package com.example.weatherapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapp.ViewModel.MainViewModel
import com.example.weatherapp.Views.MainScreen

@Composable
fun WeatherApp(navController: NavHostController) {

    val weatherViewModel: MainViewModel = viewModel()
    val viewStateForecast by weatherViewModel.forecastState
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(viewModelForecast = viewStateForecast, navigationToCityListScreen =  {
                navController.navigate(Screen.CityListScreen.route)
            })
        }
        composable(Screen.CityListScreen.route) {
            CityListScreen {
                navController.navigate(Screen.MainScreen.route)
            }
        }
    }
}