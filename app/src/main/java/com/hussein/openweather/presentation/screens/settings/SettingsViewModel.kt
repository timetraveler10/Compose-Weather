package com.hussein.openweather.presentation.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hussein.openweather.data.preferences.DatastoreManager
import com.hussein.openweather.ext.combineAsState
import com.hussein.openweather.data.location.UserLocationRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class SettingsViewModel(
    val userLocationRepository: UserLocationRepository,
    val datastoreManager: DatastoreManager
) : ViewModel() {

    private val TAG = "SettingsViewModel"


    val state = combineAsState(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SettingsScreenState(true),
        userLocationRepository.userLocationFlow,
        datastoreManager.autoUpdateLocation,
        datastoreManager.isDynamicThemeEnabled,
        userLocationRepository.locationProviderFlow
    ) { userLocation, autoUpdate, isDynamicColorEnabled, isLocationProviderEnabled, ->
        SettingsScreenState(
            userLocation = userLocation,
            autoUpdate = autoUpdate,
            locationProviderEnabled = isLocationProviderEnabled,
            dynamicThemeEnabled = isLocationProviderEnabled,
            isLoading = false ,
        )
    }



    fun onAction(actions: SettingScreenActions) {
        when (actions) {
            is SettingScreenActions.ToggleAutoUpdate -> toggleAutoUpdate(actions.autoUpdate)
            is SettingScreenActions.UpdateLocation -> updateLocation()

            is SettingScreenActions.ToggleDynamicColor -> onDynamicColorChange(actions.useM3)
        }
    }

    fun toggleAutoUpdate(autoUpdate: Boolean) {
        viewModelScope.launch {
            datastoreManager.setAutoUpdatePref(autoUpdate)
        }
    }

    fun onDynamicColorChange(useM3: Boolean) {
        viewModelScope.launch {
            datastoreManager.setDynamicThemePref(useM3)
        }
    }



    fun updateLocation() {
        viewModelScope.launch {
            userLocationRepository.getUserLocation()
                .onSuccess { location ->
                    datastoreManager.setUserLocationPref(location)
                }.onFailure {
                    Log.d("SettingsViewModel", "Error while saving location")
                }
        }

    }

}

sealed interface SettingScreenActions {

    object UpdateLocation : SettingScreenActions
    data class ToggleDynamicColor(val useM3: Boolean) : SettingScreenActions
    data class ToggleAutoUpdate(val autoUpdate: Boolean) : SettingScreenActions

}
