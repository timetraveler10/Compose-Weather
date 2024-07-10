package com.hussein.openweather.data.model.weather_api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherApiReponse(
    @SerialName("current")
    val current: Current,
    @SerialName("forecast")
    val forecast: Forecast,
    @SerialName("location")
    val location: Location
)