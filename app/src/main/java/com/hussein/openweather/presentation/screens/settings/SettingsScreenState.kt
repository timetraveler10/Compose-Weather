package com.hussein.openweather.presentation.screens.settings

import com.hussein.openweather.data.preferences.UserLocation
import com.hussein.openweather.domain.PrecipitationUnits
import com.hussein.openweather.domain.SpeedUnits
import com.hussein.openweather.domain.TemperatureUnits
import com.hussein.openweather.domain.WeatherUnits

data class SettingsScreenState(
    val isLoading: Boolean = true,
    val userLocation: UserLocation? = null,
    val autoUpdate: Boolean = false,
    val locationProviderEnabled: Boolean = true,
    val dynamicThemeEnabled: Boolean = false,
)