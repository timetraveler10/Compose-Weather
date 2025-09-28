package com.hussein.openweather.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.hussein.openweather.data.preferences.DatastoreManager
import com.hussein.openweather.data.preferences.UserLocation
import com.hussein.openweather.ext.isProviderEnabled
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import java.util.Locale


class UserLocationRepository(
    private val context: Context,
    private val datastoreManager: DatastoreManager
) {

    private val TAG = "UserLocationManager"

    val userLocationFlow: Flow<UserLocation?> = datastoreManager.userLocationFlow

    private val geoCoder = Geocoder(context, Locale.getDefault())

    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    val locationProviderFlow: Flow<Boolean> = callbackFlow {
        val isProviderEnabled = context.isProviderEnabled()
        val locationProviderReceiver = object : BroadcastReceiver() {
            override fun onReceive(
                context: Context?,
                intent: Intent
            ) {
                if (intent.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
                    trySend(isProviderEnabled)
                    Log.d(TAG, "Location provider state: $isProviderEnabled")
                }
            }
        }
        val intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        ContextCompat.registerReceiver(
            context,
            locationProviderReceiver,
            intentFilter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
        trySend(isProviderEnabled)
        awaitClose {
            context.unregisterReceiver(locationProviderReceiver)
        }
    }


    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private suspend fun getRawLocation(): UserLocation {

        val currentLocationRequest = CurrentLocationRequest
            .Builder()
            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .setDurationMillis(1000)
            .build()


        val location = fusedLocationProviderClient.getCurrentLocation(
            currentLocationRequest,
            object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken =
                    CancellationTokenSource().token

                override fun isCancellationRequested(): Boolean = false
            }
        ).await() ?: throw LocationExceptions.LocationNotAvailable

        return UserLocation(
            latitude = location.latitude,
            longitude = location.longitude,
            addressName = extractAddressFromGeocoder(location)
        )
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("MissingPermission")
    suspend fun getUserLocation(): Result<UserLocation> {
        val cachedLocation = datastoreManager.userLocationFlow.firstOrNull()

        if (cachedLocation == null) {
            val rawLocation = getRawLocation()
            datastoreManager.setUserLocationPref(rawLocation)
            return Result.success(rawLocation)
        }
        return Result.success(cachedLocation)
    }

    private fun extractAddressFromGeocoder(location: Location): String {
        val address = geoCoder.getFromLocation(
            location.latitude,
            location.longitude,
            1
        )?.get(0)

        return "${address?.subAdminArea}, ${address?.adminArea}"
    }
}