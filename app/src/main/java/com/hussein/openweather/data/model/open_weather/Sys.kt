package com.hussein.openweather.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable

data class Sys(
    @SerialName("country")
    val country: String,
    @SerialName("sunrise")
    val sunrise: Int,
    @SerialName("sunset")
    val sunset: Int
)