package com.hussein.openweather.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeather(
    val temp: Int,
    val feelsLike: Int,
    val humidity: Int,
    val pressure: Double,
    val weatherCondition: WeatherCondition,
    val wind: Wind
)