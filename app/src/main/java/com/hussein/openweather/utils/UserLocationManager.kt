package com.hussein.openweather.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.LocationServices

sealed class LocationRequestState {
    data object UnableToGetLocation : LocationRequestState()
    data object ProviderNotEnabled : LocationRequestState()
    data class Success(val location: Location) : LocationRequestState()
    data object PermissionNotGranted : LocationRequestState()
}

class UserLocationManager(
    context: Context,
    val onError: (message: String) -> Unit,
    val onSuccess: (Location) -> Unit
) {
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager


    @SuppressLint("MissingPermission")
    fun getLocation() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        ) {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    onSuccess(task.result)
                } else{
                    onError("Error getting your location")
                }
            }
        } else {
            onError("Provider Not Available")
        }
    }
}