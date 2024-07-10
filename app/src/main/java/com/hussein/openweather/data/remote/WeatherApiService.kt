package com.hussein.openweather.data

import com.hussein.openweather.data.model.open_weather.OpenWeatherApiResponse
import com.hussein.openweather.location.UserLocation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object WeatherApiService {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })

        }
        install(Logging)
    }

    suspend fun getWeather(userLocation: UserLocation): Result<OpenWeatherApiResponse> {
        return runCatching {
            val result =
                client.get(urlString = "https://api.openweathermap.org/data/2.5/weather?appid=6baba60ac76ba1ef52815e6b3ca1fe1c&units=metric")
                {
                    parameter("lat" , value = userLocation.lat)
                    parameter(key = "lon" , value = userLocation.lng)
                }
            result.body<OpenWeatherApiResponse>()
        }
    }

    fun closeClient() = client.close()

}