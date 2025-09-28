package com.hussein.openweather.domain.models

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

@Serializable
data class HourlyWeather(
    val time: String, val temp: Int, val weatherCode: Int, @DrawableRes val icon: Int
)