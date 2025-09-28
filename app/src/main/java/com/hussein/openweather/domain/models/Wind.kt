package com.hussein.openweather.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Wind(val speed:Int , val direction: Int)