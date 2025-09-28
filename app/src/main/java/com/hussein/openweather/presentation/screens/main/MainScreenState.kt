package com.hussein.openweather.presentation.screens.main

import com.hussein.openweather.domain.models.ForecastWeather

data class MainScreenState(
    val data: ForecastWeather? = null,
    val loading: Boolean = false,
    val error: Throwable? = null,
    val timestamp:String? = null
)