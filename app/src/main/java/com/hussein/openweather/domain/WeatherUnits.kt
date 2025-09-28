package com.hussein.openweather.domain

import kotlinx.serialization.Serializable

data class WeatherUnits(
    val temp: TemperatureUnits = TemperatureUnits.Celsius,
    val windSpeed: SpeedUnits = SpeedUnits.KmH,
    val precipitation: PrecipitationUnits = PrecipitationUnits.Mm
)


enum class TemperatureUnits() {
    Celsius(), Fahrenheit()
}


enum class SpeedUnits() {
    KmH(), Mph(), MS()
}


enum class PrecipitationUnits() {
    Mm(), Inch()

}