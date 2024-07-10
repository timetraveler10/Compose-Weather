package com.hussein.openweather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.hussein.openweather.MyApplication
import com.hussein.openweather.data.WeatherApiService
import com.hussein.openweather.location.LocationRequestExceptions
import com.hussein.openweather.location.UserLocation
import com.hussein.openweather.location.UserLocationRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(val locationRepository: UserLocationRepository) : ViewModel() {
    val uiState = MutableStateFlow(WeatherUIState())
    val uiEvents = Channel<UIStates>()

    init {
        onEvent(UiEvents.RefreshWeather)
    }


    private fun getWeather() {
        viewModelScope.launch {
            val locationResult = locationRepository.getLocation()
            if (locationResult.isSuccess) {

                val result = WeatherApiService.getWeather(locationResult.getOrThrow())

                result.onSuccess {
                    uiState.update {
                        WeatherUIState(data = result.getOrThrow(), loading = false)
                    }
                }.onFailure {
                    uiState.update {
                        WeatherUIState(
                            loading = false,
                            error = result.exceptionOrNull()
                        )
                    }
                }
            } else {
                locationResult.exceptionOrNull()?.let { e ->
                    uiState.update {
                        WeatherUIState(
                            loading = false,
                            error = e
                        )
                    }

                }
            }

        }
    }


    fun onEvent(events: UiEvents) {
        when (events) {
            is UiEvents.RefreshWeather -> {
                getWeather()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MainViewModel((application as MyApplication).userLocationRepository) as T
            }
        }
    }
}

sealed class UIStates() {
    data class Error(val errMsg: String) : UIStates()
    data class Success(val userLocation: UserLocation) : UIStates()

}

sealed class UiEvents() {
    data object RefreshWeather : UiEvents()
}
