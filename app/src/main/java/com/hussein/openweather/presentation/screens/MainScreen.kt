package com.hussein.openweather.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hussein.openweather.dummy_weather_data
import com.hussein.openweather.location.LocationRequestExceptions
import com.hussein.openweather.presentation.components.ErrorScreen
import com.hussein.openweather.presentation.components.LoadingScreen
import com.hussein.openweather.presentation.components.SlidingText
import com.hussein.openweather.presentation.components.WeatherInfoScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: WeatherUIState,
    onRefresh: () -> Unit,
    onProviderNotEnabled: () -> Unit,
    onPermissionNotGranted: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var topAppBarTitle by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .padding(), topBar = {

        LargeTopAppBar(

            title = { SlidingText(text = topAppBarTitle) },
            scrollBehavior = scrollBehavior,
            actions = {
                IconButton(
                    onClick = onRefresh
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                }
            })

    }) { innerPadding ->


        AnimatedContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            targetState = uiState,
            label = "Main content"
        ) {

            when {

                it.error != null -> {

                    topAppBarTitle = "Error"
                    when (it.error) {
                        is LocationRequestExceptions.ProviderError -> {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Gps is not enabled , please enable and try again")
                                Button(onClick = { onProviderNotEnabled() }) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = null
                                    )
                                }
                                Button(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = null
                                    )
                                }

                            }
                        }

                        is LocationRequestExceptions.MissingPermission -> {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Permission is not granted or permanently denied , please enable")
                                Button(onClick = { onPermissionNotGranted()}) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = null
                                    )
                                }
                            }
                        }

                        is LocationRequestExceptions.LocationError -> {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Error getting your location , please retry again")
                                Button(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = null
                                    )
                                }
                            }

                        }
                    }
                }

                it.data != null -> {
                    topAppBarTitle = "Weather in " + uiState.data!!.name
                    WeatherInfoScreen(weatherApiResponse = it.data)
                }

                it.loading -> {
                    topAppBarTitle = "Loading..."
                    LoadingScreen()
                }
            }
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        uiState = WeatherUIState(data = dummy_weather_data, loading = false),
        onRefresh = {},
        onProviderNotEnabled = {} , onPermissionNotGranted = {})
}