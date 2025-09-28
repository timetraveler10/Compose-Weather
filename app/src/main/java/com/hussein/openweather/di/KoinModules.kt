package com.hussein.openweather.di

import com.hussein.openweather.data.WeatherSourceRepository
import com.hussein.openweather.data.preferences.DatastoreManager
import com.hussein.openweather.presentation.screens.main.MainViewModel
import com.hussein.openweather.presentation.screens.onboarding.OnboardingViewModel
import com.hussein.openweather.presentation.screens.settings.SettingsViewModel
import com.hussein.openweather.data.location.UserLocationRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<DatastoreManager> { DatastoreManager(get()) }
    single<WeatherSourceRepository> { WeatherSourceRepository(get() , get()) }
    single<UserLocationRepository> { UserLocationRepository(get() , get()) }

    viewModel<SettingsViewModel> { SettingsViewModel(get(), get()) }
    viewModel<MainViewModel> { MainViewModel(get()) }
    viewModel<OnboardingViewModel> { OnboardingViewModel(get(), get()) }


}