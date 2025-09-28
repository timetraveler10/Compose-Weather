package com.hussein.openweather.domain.models

import androidx.datastore.core.Serializer
import com.hussein.openweather.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object ForecastWeatherSerializer : Serializer<ForecastWeather> {
    override val defaultValue: ForecastWeather
        get() = ForecastWeather(
            hourly = listOf(),
            daily = listOf(),
            current = CurrentWeather(
                temp = 0,
                feelsLike = 0,
                humidity = 0,
                pressure = 0.0,
                weatherCondition = WeatherCondition("", R.drawable.clear_n),
                wind = Wind(0, 0)
            ),
            lastUpdated = null,
        )

    override suspend fun readFrom(input: InputStream): ForecastWeather {
        return try {
            Json.decodeFromString(
                deserializer = ForecastWeather.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: ForecastWeather, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(serializer = ForecastWeather.serializer(), value = t)
                    .encodeToByteArray()
            )
        }
    }
}