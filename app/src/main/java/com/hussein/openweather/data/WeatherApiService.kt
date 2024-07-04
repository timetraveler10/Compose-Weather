package com.hussein.openweather.utils

import com.hussein.openweather.data.model.Weather
import com.hussein.openweather.data.model.WeatherApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object WeatherApiService {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })

        }
    }

    suspend fun getWeather(): Result<WeatherApiResponse> {
        return runCatching {
            val result =
                client.get(urlString = "https://api.openweathermap.org/data/2.5/weather?lat=31.1866&lon=32.0322&appid=6baba60ac76ba1ef52815e6b3ca1fe1c&units=metric")
            result.body<WeatherApiResponse>()
        }
    }

    fun closeClient() = client.close()

}