package com.example.weatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CityListScreen(navigationToMainScreen: () -> Unit) {
    val itemsList = (0..5).toList()
    var text by remember { mutableStateOf("") }
    Image(
        painter = painterResource(id = R.drawable.night),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Weather",
                fontSize = 36.sp,
                color = Color(220, 230, 253, 255)
            )
            Button(onClick = { navigationToMainScreen() }) {
                Icon(Icons.Default.ArrowBack, "")
            }
        }
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                leadingIconColor = Color(220, 230, 253, 255),
                focusedBorderColor = Color(220, 230, 253, 255),
                unfocusedBorderColor = Color(220, 230, 253, 255),
                cursorColor = Color(220, 230, 253, 255),
                textColor = Color(220, 230, 253, 255)
            ),
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "")
            }
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(4) {
                CityListScreenItem()
            }
        }
    }
}

@Composable
fun CityListScreenItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color(108, 113, 124, 128), shape = MaterialTheme.shapes.medium)
            .clickable { },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "19", fontSize = 48.sp, color = Color(220, 230, 253, 255))
            Text(text = "Image", fontSize = 48.sp, color = Color(220, 230, 253, 255))
        }
        Text(
            text = "H: 25° L: 19°",
            fontSize = 18.sp,
            color = Color(151, 157, 172, 255),
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Light
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Location", fontSize = 24.sp, color = Color(220, 230, 253, 255))
            Text(text = "Mid Rain", fontSize = 18.sp, color = Color(220, 230, 253, 255))
        }
    }
}
