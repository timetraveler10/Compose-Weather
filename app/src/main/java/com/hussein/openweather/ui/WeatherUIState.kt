package com.hussein.openweather.ui

import com.hussein.openweather.model.WeatherApiResponse

data class WeatherUIState(
    val data: WeatherApiResponse,
    val loading: Boolean,
    val error: String? = null
)