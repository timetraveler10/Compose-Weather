package com.hussein.openweather.presentation.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hussein.openweather.domain.models.DailyWeather
import com.hussein.openweather.utils.parseDate

@Composable
fun DetailsScreenContent(
    cardModifier: Modifier,
    lazyRowModifier: Modifier,
    innerColumnModifier: Modifier,
    dailyWeather: List<DailyWeather>,
    onWeatherItemClick: (Int) -> Unit,
    selectedDailyWeather: DailyWeather
) {
    Card(
        modifier = cardModifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            LazyRow(
                modifier = lazyRowModifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(dailyWeather) { index, item ->
                    DailyWeatherListItem(
                        data = item,
                        onItemClick = { onWeatherItemClick(index) }
                    )
                }
            }



            Column(
                modifier = innerColumnModifier
            ) {

                Text(
                    text = parseDate(
                        date = selectedDailyWeather.date,
                    ),
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Medium),
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "${selectedDailyWeather.maxTemp}°/${selectedDailyWeather.minTemp}°",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                HourlyWeatherCard(data = selectedDailyWeather.hourlyWeather)
            }

        }

    }

}