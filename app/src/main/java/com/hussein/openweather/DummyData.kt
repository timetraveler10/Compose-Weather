package com.hussein.openweather

import com.hussein.openweather.model.Clouds
import com.hussein.openweather.model.Coord
import com.hussein.openweather.model.Main
import com.hussein.openweather.model.Sys
import com.hussein.openweather.model.Weather
import com.hussein.openweather.model.WeatherApiResponse
import com.hussein.openweather.model.Wind

val dummy_weather_data = WeatherApiResponse(
    coord = Coord(
        lon = 32.0322,
        lat = 31.1866
    ), weather = listOf(
        Weather(
            id = 800,
            main = "Clear",
            description = "clear sky",
            icon = "01d"
        )
    ), base = "stations",
    main = Main(
        temp = 26.12,
        feelsLike = 26.12,
        tempMin = 26.12,
        tempMax = 26.12,
        pressure = 1006,
        humidity = 76,
        seaLevel = 1006,
        grndLevel = 1006
    ), visibility = 10000, wind = Wind(
        speed = 3.9,
        deg = 312,
        gust = 4.73
    ), clouds = Clouds(0), dt = 1719803419, sys = Sys(
        country = "EG",
        sunrise = 1719802286,
        sunset = 1719853188
    ), timezone = 10800, id = 360716, name = "Al Maţarīyah", cod = 200
)