package com.hussein.openweather.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hussein.openweather.data.WeatherSourceRepository
import com.hussein.openweather.domain.mappers.toDomain
import com.hussein.openweather.ext.restartableStateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow

class MainViewModel(
    val weatherSourceRepository: WeatherSourceRepository
) : ViewModel() {



    val state = flow {
        emit(MainScreenState(loading = true))
        weatherSourceRepository.getWeather()
            .onSuccess {
            emit(MainScreenState(data = it.toDomain(), loading = false))
        }.onFailure {
            emit(MainScreenState(error = it, loading = false))
        }
    }.restartableStateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainScreenState(loading = true)
    )


    fun onEvent(events: MainScreenActions) {
        when (events) {
            is MainScreenActions.RefreshMainScreen -> {
                state.restart()
            }
        }
    }

}



