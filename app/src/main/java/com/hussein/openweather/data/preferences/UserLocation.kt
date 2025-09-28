package com.hussein.openweather.data.preferences

import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable

@Serializable
data class UserLocation(
    val latitude: Double,
    val longitude: Double,
    val addressName: String
) {
    fun toLatLng() = LatLng(latitude, longitude)
}
