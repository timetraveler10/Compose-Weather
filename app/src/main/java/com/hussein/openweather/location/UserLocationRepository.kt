package com.hussein.openweather

import com.hussein.openweather.utils.UserLocationManager
import kotlinx.coroutines.flow.single

class UserLocationRepository(
    private val userLocationManager: UserLocationManager,
    private val datastorePrefManager: DatastorePrefManager
) {
//
//    suspend fun persistLocation() {
//        val locationResult = userLocationManager.getLocation()
//        if (locationResult.isSuccess) {
//            datastorePrefManager.saveUserLocation(userLocation = locationResult.getOrThrow())
//        }
//    }

    suspend fun getLocation(): Result<Pair<Double, Double>> {
        val userLocationPref = datastorePrefManager.getUserLocation().single()
        return if (userLocationPref.first == 0.0) {
//        this is the default value , so location is not yet saved
            val locationResult = userLocationManager.getLocation()
            if (locationResult.isSuccess) {
                datastorePrefManager.saveUserLocation(userLocation = locationResult.getOrThrow())
                return Result.success(datastorePrefManager.getUserLocation().single())
            } else {
                Result.failure(locationResult.exceptionOrNull()!!)
            }
        } else {
            Result.success(userLocationPref)
        }
    }
}