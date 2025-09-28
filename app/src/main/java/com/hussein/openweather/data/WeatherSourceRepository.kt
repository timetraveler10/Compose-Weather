package com.hussein.openweather.data

import android.util.Log
import com.hussein.openweather.data.location.UserLocationRepository
import com.hussein.openweather.data.model.open_meto.OpenMeteoResponse
import com.hussein.openweather.data.preferences.DatastoreManager
import com.hussein.openweather.data.remote.WeatherApiService
import kotlinx.coroutines.flow.first

class WeatherSourceRepository(
    private val locationRepository: UserLocationRepository,
    private val datastoreManager: DatastoreManager
) {
    val TAG = "WeatherSourceRepository"

    val locationProviderFlow = locationRepository.locationProviderFlow


    suspend fun getWeather(): Result<OpenMeteoResponse> {
        val autoUpdateLocation = datastoreManager.autoUpdateLocation.first()

        val locationRequest =
            locationRepository.getUserLocation()

        val location = locationRequest.getOrNull()
        if (location != null) {
            Log.d("WeatherSourceRepository", "getWeather:$location ")
            val weather = WeatherApiService.getOpenMeteoWeather(
                location = location)
            return Result.success(weather)
        } else {
            return Result.failure(
                locationRequest.exceptionOrNull() ?: Exception("Location is null")
            )
        }
    }
}