package com.hussein.openweather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hussein.openweather.R
import com.hussein.openweather.dummy_weather_data
import com.hussein.openweather.model.WeatherApiResponse
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier, weatherApiResponse: WeatherApiResponse) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .padding(), topBar = {
        LargeTopAppBar(
            title = { Text(text = "Weather in ${weatherApiResponse.name}") },
            scrollBehavior = scrollBehavior
        )

    }) { innerPadding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Card(Modifier.fillMaxWidth()) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(80.dp),
                        painter = painterResource(id = R.drawable._0n),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = weatherApiResponse.main.temp.toInt().toString() + '°',
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Start,

                            )

                        Text(
                            text = weatherApiResponse.weather.first().main,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Start,
                        )

                        Text(
                            text = "Feels Like " + weatherApiResponse.main.feelsLike.toString() + '°',
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Start,

                            )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                MiniWeatherInfoBox(
                    modifier = Modifier.weight(1f),
                    title = "Pressure",
                    icon = R.drawable._1d,
                    value = "Some Value"
                )
                MiniWeatherInfoBox(
                    modifier = Modifier.weight(1f),
                    title = "Pressure",
                    icon = R.drawable._1d,
                    value = "Some Value"
                )

            }

        }
    }
}

@Composable
fun MiniWeatherInfoBox(modifier: Modifier = Modifier, title: String, icon: Int, value: Any) {
    Card(modifier) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = icon),
                contentDescription = null
            )
            Text(text = value.toString())


        }

    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(weatherApiResponse = dummy_weather_data)
}