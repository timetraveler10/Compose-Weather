package com.hussein.openweather.ui

import com.hussein.openweather.data.model.WeatherApiResponse

data class WeatherUIState(
    val data: WeatherApiResponse? = null,
    val loading: Boolean = true,
    val error: String? = null
)