package com.hussein.openweather.data.location

sealed class LocationExceptions(message: String) : Exception(message) {
    data object ProviderError : LocationExceptions(message = "Provider Error")
    data object LocationNotAvailable : LocationExceptions(message = "Location not available")
}