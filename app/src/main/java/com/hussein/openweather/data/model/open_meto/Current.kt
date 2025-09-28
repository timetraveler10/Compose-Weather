package com.hussein.openweather.data.model.open_meto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Current(
    @SerialName("apparent_temperature")
    val apparentTemperature: Double,
    @SerialName("interval")
    val interval: Int,
    @SerialName("relative_humidity_2m")
    val relativeHumidity2m: Int,
    @SerialName("surface_pressure")
    val surfacePressure: Double,
    @SerialName("temperature_2m")
    val temperature2m: Double,
    @SerialName("time")
    val time: String,
    @SerialName("weather_code")
    val weatherCode: Int,
    @SerialName("wind_speed_10m")
    val windSpeed10m: Double,
    @SerialName("is_day")
    val isDay: Int ,
    @SerialName("wind_direction_10m")
    val windDirection: Int
)