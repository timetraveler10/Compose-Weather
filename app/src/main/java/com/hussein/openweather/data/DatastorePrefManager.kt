package com.hussein.openweather

import android.content.Context
import android.location.Location
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "user_pref")

class DatastorePrefManager(context: Context) {
    val dataStore = context.datastore

    val USER_LAT = doublePreferencesKey("lat")
    val USER_LNG = doublePreferencesKey("lng")

    suspend fun saveUserLocation(userLocation: Location) {
        dataStore.edit { userPrefs ->
            userPrefs[USER_LAT] = userLocation.latitude
            userPrefs[USER_LNG] = userLocation.longitude
        }
    }

    //    returns lat , lng like the previous order , A is lat , B is Long
    fun getUserLocation(): Flow<Pair<Double, Double>> = dataStore.data.map { userPrefs ->
            val lat = userPrefs[USER_LAT] ?: 0.0
            val lng = userPrefs[USER_LNG] ?: 0.0
            Pair(first = lat, second = lng)
        }
}