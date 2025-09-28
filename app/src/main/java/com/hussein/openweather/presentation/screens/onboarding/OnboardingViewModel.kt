package com.hussein.openweather.presentation.screens.onboarding

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hussein.openweather.data.location.UserLocationRepository
import com.hussein.openweather.data.preferences.DatastoreManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch


class OnboardingViewModel(
    val locationRepository: UserLocationRepository,
    val datastoreManager: DatastoreManager
) : ViewModel() {

    val locationProviderFlow =
        locationRepository.locationProviderFlow.shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly
        )


    @SuppressLint("MissingPermission")
    fun saveLocation() {
        viewModelScope.launch {
            locationRepository.getUserLocation().onSuccess {
                datastoreManager.setOnboardingCompletedState(true)
            }
        }
    }


}

