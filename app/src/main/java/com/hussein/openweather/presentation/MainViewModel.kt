package com.hussein.openweather.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.hussein.openweather.MyApplication
import com.hussein.openweather.UserLocationRepository
import com.hussein.openweather.utils.UserLocationManager
import com.hussein.openweather.utils.WeatherApiService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(val locationRepository: UserLocationRepository) : ViewModel() {
    val uiState = MutableStateFlow(WeatherUIState())
    val uiEvents = Channel<UIEvents>()
    var location by mutableStateOf("")

    init {
        getWeather()
    }


    fun getLocation() {
        viewModelScope.launch {
            val result = locationRepository.getLocation()
            result.onSuccess {
                location = result.getOrThrow().toString()
                Log.d("MainViewModel", "getLocation:${result.getOrThrow()} ")
            }.onFailure {
                uiEvents.send(UIEvents.Error)
                Log.d("MainViewModel", "Failed")

            }
        }
    }

    private fun getWeather() {
        viewModelScope.launch {
            val result = WeatherApiService.getWeather()
            result.onSuccess {
                uiState.update {
                    WeatherUIState(data = result.getOrThrow(), loading = false)
                }
            }.onFailure {
                uiState.update {
                    WeatherUIState(
                        data = null,
                        loading = false,
                        error = result.exceptionOrNull()?.message ?: "Some error happened"
                    )
                }

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

sealed class UIEvents() {
    data object Error : UIEvents()

}
