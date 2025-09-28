package com.hussein.openweather.data.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.hussein.openweather.ext.appPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


class DatastoreManager(context: Context) {
    val TAG = "DatastoreManager"

    private val appPreferences = context.appPreferences

    private val LAT = doublePreferencesKey("lat")
    private val LNG = doublePreferencesKey("lng")
    private val ADDRESS_NAME = stringPreferencesKey("address_name")

    private val autoUpdateWeatherPrefKey = booleanPreferencesKey("auto_update_location")

    private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")

    private val useDynamicThemePrefKey = booleanPreferencesKey("dynamic_theme_enabled")


    val onBoardingCompletedState = appPreferences.data.map {
        it[ONBOARDING_COMPLETED] ?: false
    }

    val isDynamicThemeEnabled = appPreferences.data.map {
        it[useDynamicThemePrefKey] == true
    }


    val autoUpdateLocation = appPreferences.data.map {
        it[autoUpdateWeatherPrefKey] ?: false
    }

    val userLocationFlow: Flow<UserLocation?> =
        appPreferences.data
            .catch {
                Log.d(TAG, "${it.message}")
                emit(emptyPreferences())
            }
            .map { preferences ->
                val lat = preferences[LAT]
                val lng = preferences[LNG]
                val addressName = preferences[ADDRESS_NAME]
                if (lat == null || lng == null) {
                    return@map null
                } else {
                    UserLocation(
                        latitude = preferences[LAT] ?: 0.0,
                        longitude = preferences[LNG] ?: 0.0,
                        addressName = addressName ?: "Unknown Location"
                    )
                }
            }


    suspend fun setOnboardingCompletedState(value: Boolean) {
        appPreferences.edit {
            it[ONBOARDING_COMPLETED] = value
        }
    }

    suspend fun setUserLocationPref(userLocation: UserLocation) {
        Log.d(TAG, "saveUserLocation: $userLocation")
        appPreferences.edit { preferences ->
            preferences[LAT] = userLocation.latitude
            preferences[LNG] = userLocation.longitude
            preferences[ADDRESS_NAME] = userLocation.addressName
        }

    }




    suspend fun setAutoUpdatePref(autoUpdate: Boolean) {
        appPreferences.edit {
            it[autoUpdateWeatherPrefKey] = autoUpdate
        }
    }

    suspend fun setDynamicThemePref(value: Boolean) {
        appPreferences.edit {
            it[useDynamicThemePrefKey] = value
        }
    }


}

