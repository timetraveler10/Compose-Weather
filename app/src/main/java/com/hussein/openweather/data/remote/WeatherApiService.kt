package com.hussein.openweather.data.remote

import com.hussein.openweather.data.model.open_meto.OpenMeteoResponse
import com.hussein.openweather.data.preferences.UserLocation
import com.hussein.openweather.domain.WeatherUnits
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object WeatherApiService {
    private const val OPEN_METEO_WEATHER_BASE_URL =
        "https://api.open-meteo.com/v1/forecast?current=is_day,temperature_2m,wind_speed_10m,wind_direction_10m,relative_humidity_2m,apparent_temperature,weather_code,surface_pressure,wind_speed_10m&hourly=is_day,temperature_2m,weather_code&daily=weather_code,temperature_2m_max,temperature_2m_min&timezone=Africa%2FCairo"

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) { level = LogLevel.ALL }
    }

    suspend fun getOpenMeteoWeather(
        location: UserLocation,
    ): OpenMeteoResponse {

        val result =
            client.get(urlString = OPEN_METEO_WEATHER_BASE_URL) {
                parameter(key = "latitude", value = "${location.latitude}")
                parameter(key = "longitude", value = "${location.longitude}")
//                parameter(key = "wind_speed_unit" , value = weatherUnits.windSpeed.name)
//                parameter(key = "temperature_unit" , value = weatherUnits.temp.name)
            }
        return result.body<OpenMeteoResponse>()
    }

    fun closeClient() = client.close()

}