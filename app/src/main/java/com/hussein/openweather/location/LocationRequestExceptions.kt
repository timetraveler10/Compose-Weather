package com.hussein.openweather.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

sealed class LocationRequestExceptions(msg: String) : Throwable(message = msg) {
    data object LocationError : LocationRequestExceptions(msg = "Error getting your location")
    data object ProviderError : LocationRequestExceptions(msg = "Provider not available")
    data object MissingPermission :
        LocationRequestExceptions(msg = "Location permission not available")

    data object UnknownError : LocationRequestExceptions(msg = "Unknown")
}

class UserLocationManager(
    private val context: Context
) {
    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager





    @SuppressLint("MissingPermission")
    fun getLocationFromFusedLocationProvider(): Task<Location> {
////        if (isPermissionGranted()) {
//////            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//////            ) {
//////
//////                val lastLocation = fusedLocationProviderClient.lastLocation
//////
//////                if (lastLocation.isSuccessful && lastLocation.isComplete) {
//////                    Result.success(lastLocation.result)
//////                } else {
//////                    Result.failure(LocationRequestExceptions.LocationError)
//////                }
//////            } else {
//////                Result.failure(LocationRequestExceptions.ProviderError)
//////            }
//////        } else {
//////            Result.failure(LocationRequestExceptions.MissingPermission)
//////        }
//        }
        return fusedLocationProviderClient.lastLocation

    }
}