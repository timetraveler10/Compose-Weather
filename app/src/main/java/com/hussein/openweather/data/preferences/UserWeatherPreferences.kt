package com.hussein.openweather.data

import android.content.Context
import android.location.Location
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hussein.openweather.location.UserLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "user_pref")

class DatastorePrefManager(context: Context) {
    val dataStore = context.datastore

    private val PREFERENCE_LAT = doublePreferencesKey("lat")
    private val PREFERENCE_LNG = doublePreferencesKey("lng")
    private val PREFERENCE_METRICS = stringPreferencesKey("metrics")



    suspend fun saveUserLocation(userLocation: Location) {
        dataStore.edit { userPrefs ->
            userPrefs[PREFERENCE_LAT] = userLocation.latitude
            userPrefs[PREFERENCE_LNG] = userLocation.longitude
        }
    }

    fun getUserLocation(): Flow<UserLocation?> = dataStore.data.map { userPrefs ->
        val lat = userPrefs[PREFERENCE_LAT]
        val lng = userPrefs[PREFERENCE_LNG]
        if (lat != null && lng != null) {
            return@map UserLocation(lat, lng)
        } else null
    }
}