package com.hussein.openweather.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class ForecastWeather(
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>,
    val current: CurrentWeather,
    val lastUpdated: Long?
)

