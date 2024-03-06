package com.example.weatherapp.Views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.*
import com.example.weatherapp.R
import com.example.weatherapp.ViewModel.MainViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun MainScreen(
    navigationToCityListScreen: () -> Unit,
    viewModelForecast: MainViewModel /*Inject ViewModel instance*/
) {
    // Location Variables
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    // Check error, loading animation, load data from API
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewModelForecast.forecastState.value.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            viewModelForecast.forecastState.value.error != null -> {
                Text(text = "Something wrong")
            }

            else -> {
                // Call for getting coordinates
                CurrentLocation(locationUtils, context, viewModel = viewModelForecast)
                // Call for getting interface
                MainScreenItem(
                    location = viewModelForecast.forecastState.value.location,
                    current = viewModelForecast.forecastState.value.current,
                    forecast = viewModelForecast.forecastState.value.forecast,
                    navigationToCityListScreen
                )
            }
        }
    }
}

// Compose element - Main Screen
@Composable
fun MainScreenItem(
    location: Location?,
    current: Current?,
    forecast: Forecast?,
    navigationToCityListScreen: () -> Unit
) {


    // Hourly, Weekly transition and Animation
    var isHourlyExpanded by remember { mutableStateOf(true) }
    var isWeeklyExpanded by remember { mutableStateOf(false) }

    val hourlyBorder by animateDpAsState(
        targetValue = if (isHourlyExpanded) 3.dp else 1.dp
    )

    val weeklyBorder by animateDpAsState(
        targetValue = if (isWeeklyExpanded) 3.dp else 1.dp
    )
//     BG Image
    Image(
        painter = painterResource(id = R.drawable.mybg),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Main Text
        Spacer(modifier = Modifier.height(125.dp))
        Text(
            buildAnnotatedString {
                val spanStyleMain = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 32.sp)
                withStyle(style = spanStyleMain) {
                    append("${location?.name}, ")
                }
                val spanStyleSecond = SpanStyle(fontSize = 24.sp)
                withStyle(style = spanStyleSecond) {
                    append("${location?.country}")
                }
            },
            color = Color(216, 220, 228, 255),
            textAlign = TextAlign.Center
        )
        Text(
            text = "${current?.temp_c?.roundToInt()}°",
            fontSize = 56.sp,
            fontWeight = FontWeight.Light,
            color = Color(216, 220, 228, 255),
            textAlign = TextAlign.Center
        )
        Text(
            text = "${current?.condition?.text}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            color = Color(216, 220, 228, 255),
            textAlign = TextAlign.Center
        )
        Text(
            text = "H: ${forecast?.forecastday?.getOrNull(0)?.day?.maxtemp_c?.roundToInt()}° L: ${
                forecast?.forecastday?.getOrNull(
                    0
                )?.day?.mintemp_c?.roundToInt()
            }°",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color(220, 230, 253, 255),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(49, 53, 63, 128),
                    shape = MaterialTheme.shapes.medium
                ) // BG and Round Corners
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                // Hourly, Weekly Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Hourly Forecast",
                        color = Color(216, 220, 228, 255),
                        modifier = Modifier
                            .clickable {
                                isHourlyExpanded = true
                                isWeeklyExpanded = false
                            }
                            .animateContentSize()
                            .border(
                                BorderStroke(hourlyBorder, Color(216, 220, 228, 255)),
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(8.dp)
                    )
                    Text(
                        text = "Weekly Forecast",
                        color = Color(216, 220, 228, 255),
                        modifier = Modifier
                            .clickable {
                                isWeeklyExpanded = true
                                isHourlyExpanded = false
                            }
                            .animateContentSize()
                            .border(
                                BorderStroke(weeklyBorder, Color(216, 220, 228, 255)),
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(8.dp)
                    )
                }
                // Row with scroll for hourly/weekly forecast
                LazyRow(
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxWidth()
                        .background(Color(31, 60, 75, 150), shape = MaterialTheme.shapes.medium),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                    // We take 24 items to make a 24 hour forecast for hourly forecast
                ) {
                    if (isHourlyExpanded) {
                        items(forecast?.forecastday?.getOrNull(0)?.hour?.take(24) ?: emptyList())
                        { hourlyForecast ->
                            ForecastHourlyItem(hourlyForecast = hourlyForecast)
                        }
                    } else {
                        items(forecast?.forecastday ?: emptyList()) { weeklyForecast ->
                            ForecastWeeklyItem(forecast = weeklyForecast)
                            // Access to ForecastWeeklyItem
                        }
                    }
                }
                // Buttons Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                            Color(
                                23,
                                38,
                                48,
                                255
                            )
                        )
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "",
                            tint = Color(216, 220, 228, 255)
                        )
                    }
                    Button(
                        onClick = {
                            navigationToCityListScreen()
                        }, colors = ButtonDefaults.buttonColors(
                            Color(
                                23,
                                38,
                                48,
                                255
                            )
                        )
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "",
                            tint = Color(216, 220, 228, 255)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastHourlyItem(hourlyForecast: Hour) {
    val urlImage = "https:"
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = getFormatterDateForTime(hourlyForecast.time, "HH:mm"),
            color = Color(216, 220, 228, 255)
        )
        Image(
            painter = rememberAsyncImagePainter(
                model = (urlImage + hourlyForecast.condition.icon)
            ),
            contentDescription = "Weather Icon",
            modifier = Modifier
                .height(50.dp)
                .aspectRatio(1f)
                .padding(8.dp)
        )
        Text(
            text = "${hourlyForecast.temp_c.roundToInt()}°",
            color = Color(216, 220, 228, 255)
        )
    }
}


@Composable
fun ForecastWeeklyItem(forecast: ForecastDay) {
    val urlImage = "https:"

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
    ) {
        Text(
            text = getFormatterDateForDay(forecast.date),
            color = Color(216, 220, 228, 255)
        )
        Image(
            painter = rememberAsyncImagePainter(
                model = (urlImage + forecast.day.condition.icon)
            ),
            "Weather Icon",
            modifier = Modifier
                .height(50.dp)
                .aspectRatio(1f)
                .padding(8.dp)
        )
        Text(
            text = "${forecast.day.avgtemp_c.roundToInt()}°",
            color = Color(216, 220, 228, 255)
        )
    }
}


@Composable
fun CurrentLocation(locationUtils: LocationUtils, context: Context, viewModel: MainViewModel) {
    val location = viewModel.location.value

    // Need to show pop up for permission granting or access location if have permission
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                // Access to location
                locationUtils.requestLocationUpdates(viewModel = viewModel)
            } else {
                // Ask for permission
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                // If user presses deny - we show toast
                if (rationaleRequired) {
                    Toast.makeText(
                        context,
                        "Location Permission is required for current location weather. Change it in settings manually.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    )

//     Call it when function called
    DisposableEffect(Unit) {
        if (!locationUtils.hasLocationPermission(context)) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        } else {
            locationUtils.requestLocationUpdates(viewModel)
        }
        onDispose {}
    }

}