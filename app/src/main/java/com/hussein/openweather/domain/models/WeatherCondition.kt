package com.hussein.openweather.domain.models

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

@Serializable
data class WeatherCondition(val text: String, @DrawableRes val icon: Int)