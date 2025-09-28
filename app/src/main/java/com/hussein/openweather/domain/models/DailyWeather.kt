package com.hussein.openweather.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class DailyWeather(
    val date: String,
    val minTemp: Int,
    val maxTemp: Int,
    val code: Int,
    val hourlyWeather: List<HourlyWeather>
)