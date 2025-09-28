package com.hussein.openweather.presentation.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.hussein.openweather.R

import com.hussein.openweather.domain.models.HourlyWeather
import com.hussein.openweather.utils.parseToLocalTime

@Composable
fun HourlyWeatherCard(
    data: List<HourlyWeather>) {
    Card(shape = RoundedCornerShape(18.dp)) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(imageVector = Icons.Default.AccessTime, contentDescription = null)
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = "Hourly Forecast",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(data) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            8.dp,
                            alignment = Alignment.CenterVertically
                        ), horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            modifier = Modifier.padding(bottom = 8.dp),
                            text = it.temp.toString() + '°',
                            style = MaterialTheme.typography.labelLarge,
                            textAlign = TextAlign.Center
                        )

                        Icon(
                            modifier = Modifier.size(38.dp),
                            painter = painterResource(id = it.icon),
                            contentDescription = null
                        )

                        Text(
                            modifier = Modifier.wrapContentSize(),
                            text = parseToLocalTime(it.time),
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                }

            }


        }

    }

}


@Preview
@Composable
private fun PreviewHourlyWeatherCard(modifier: Modifier = Modifier.wrapContentSize()) {
    HourlyWeatherCard(
        data = listOf(
            HourlyWeather(
                time = "2022-07-01T01:00",
                temp = 1742,
                weatherCode = 9274,
                icon = R.drawable.clear_n
            ), HourlyWeather(
                time = "2022-07-01T01:00",
                temp = 1742,
                weatherCode = 9274,
                icon = R.drawable.clear_n
            ), HourlyWeather(
                time = "2022-07-01T01:00",
                temp = 1742,
                weatherCode = 9274,
                icon = R.drawable.clear_n
            )
        )
    )
}