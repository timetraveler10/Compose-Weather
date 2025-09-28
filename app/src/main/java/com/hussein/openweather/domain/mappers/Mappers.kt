package com.hussein.openweather.domain.mappers

import com.hussein.openweather.R
import com.hussein.openweather.data.model.open_meto.OpenMeteoResponse
import com.hussein.openweather.domain.models.CurrentWeather
import com.hussein.openweather.domain.models.DailyWeather
import com.hussein.openweather.domain.models.ForecastWeather
import com.hussein.openweather.domain.models.HourlyWeather
import com.hussein.openweather.domain.models.WeatherCondition
import com.hussein.openweather.domain.models.Wind
import com.hussein.openweather.utils.compareDates
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun OpenMeteoResponse.toDomain(): ForecastWeather {


    val allHourly = hourly.time.mapIndexed { index, time ->
        HourlyWeather(
            time = time,
            temp = hourly.temperature2m[index].toInt(),
            weatherCode = hourly.weatherCode[index],
            icon = parseCodeToWeatherCondition(
                hourly.weatherCode[index],
                isDay = hourly.isDay[index] != 0
            ).icon
        )
    }.chunked(24)

    return ForecastWeather(
        hourly = allHourly.flatten().filter {
            compareDates(it.time, current.time) // taking only from current hour and next 24 hours
        }.take(24),
        daily = daily.time.mapIndexed { index, time ->
            DailyWeather(
                date = time,
                code = parseCodeToWeatherCondition(daily.weatherCode[index], true).icon,
                maxTemp = daily.temperature2mMax[index].toInt(),
                minTemp = daily.temperature2mMin[index].toInt(),
                hourlyWeather = allHourly[index]
            )
        },
        current = CurrentWeather(
            current.temperature2m.toInt(),
            weatherCondition = parseCodeToWeatherCondition(
                current.weatherCode,
                isDay = current.isDay != 0
            ),
            feelsLike = current.apparentTemperature.toInt(),
            pressure = current.surfacePressure,
            humidity = current.relativeHumidity2m,
            wind = Wind(
                speed = current.windSpeed10m.toInt(),
                direction = current.windDirection
            )
        ),
        lastUpdated = System.currentTimeMillis(),
    )
}

fun parseCodeToWeatherCondition(code: Int, isDay: Boolean): WeatherCondition {

    val checkIsDay: (dayIcon: Int, nightIcon: Int) -> Int = { dayIcon, nightIcon ->
        if (isDay) dayIcon else nightIcon
    }
    when (code) {
        0 -> return WeatherCondition("Clear", checkIsDay(R.drawable.clear_d, R.drawable.clear_n))
        1 -> return WeatherCondition(
            "Mainly clear",
            checkIsDay(R.drawable.clear_d, R.drawable.clear_n)
        )

        2 -> return WeatherCondition(
            "Partly cloudy",
            checkIsDay(R.drawable.partly_cloudy_d, R.drawable.partly_cloudy_n)
        )

        3 -> return WeatherCondition("Overcast", R.drawable.overcast)
        45 -> return WeatherCondition("Fog", R.drawable.fog)
        51 -> return WeatherCondition("Light Drizzle", R.drawable.clear_n)
        48 -> return WeatherCondition("Depositing rime fog", R.drawable.clear_n)
        53 -> return WeatherCondition("Moderate Drizzle", R.drawable.clear_n)
        55 -> return WeatherCondition("Dense Drizzle", R.drawable.clear_n)
        56 -> return WeatherCondition("Freezing Drizzle", R.drawable.clear_n)
        57 -> return WeatherCondition("Dense Freezing Drizzle", R.drawable.clear_n)
        61 -> return WeatherCondition("Slight Rain", R.drawable.clear_n)
        63 -> return WeatherCondition("Moderate Rain", R.drawable.clear_n)
        65 -> return WeatherCondition("Heavy Rain", R.drawable.clear_n)
        66 -> return WeatherCondition("Freezing Rain", R.drawable.clear_n)
        67 -> return WeatherCondition("Freezing Freezing Rain", R.drawable.clear_n)
        71 -> return WeatherCondition("Slight Snowfall", R.drawable.clear_n)
        73 -> return WeatherCondition("Moderate Snowfall", R.drawable.clear_n)
        75 -> return WeatherCondition("Heavy Snowfall", R.drawable.clear_n)
        77 -> return WeatherCondition("Snow grains", R.drawable.clear_n)
        80 -> return WeatherCondition("Slight Rain Shower", R.drawable.clear_n)
        81 -> return WeatherCondition("Moderate Rain Shower", R.drawable.clear_n)
        82 -> return WeatherCondition("Heavy Rain Shower", R.drawable.clear_n)
        85 -> return WeatherCondition("Slight Snow Shower", R.drawable.clear_n)
        86 -> return WeatherCondition("Heavy Snow Shower", R.drawable.clear_n)
        95 -> return WeatherCondition("Thunderstorm", R.drawable.thunderstorm)
        96 -> return WeatherCondition("Thunderstorm with slight hail", 0)
        99 -> return WeatherCondition("Thunderstorm with heavy hail", 0)
        else -> return WeatherCondition("Unknown", R.drawable.clear_n)
    }
}


/*
*  the hourly forecast should be like that {time to { temp , code }}
*
*
* */
