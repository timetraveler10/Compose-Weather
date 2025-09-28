package com.hussein.openweather.presentation.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hussein.openweather.R
import com.hussein.openweather.domain.models.CurrentWeather
import com.hussein.openweather.domain.models.WeatherCondition
import com.hussein.openweather.domain.models.Wind

@Composable
fun CurrentWeatherInfo(current: CurrentWeather) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = current.temp
                    .toString() + '°',
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Medium
                ),
            )
            Icon(
                modifier = Modifier
                    .size(96.dp)
                    .padding(),
                painter = painterResource(id = current.weatherCondition.icon),
                contentDescription = null
            )
        }

    }
}

@Preview
@Composable
private fun CurrentWeatherInfoPreview(modifier: Modifier = Modifier) {
    CurrentWeatherInfo(
        current = CurrentWeather(
            temp = 21,
            feelsLike = 35,
            weatherCondition = WeatherCondition(
                text = "atomorum",
                icon = R.drawable.clear_d
            ),
            pressure = 4.5,

            humidity = 100, wind = Wind(1, 15)
        )
    )
}