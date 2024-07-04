package com.hussein.openweather.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hussein.openweather.R
import com.hussein.openweather.data.model.WeatherApiResponse

@Composable
fun WeatherInfoScreen(modifier: Modifier = Modifier , weatherApiResponse: WeatherApiResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {


        Card(Modifier.fillMaxWidth()) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = weatherApiResponse.main.temp.toInt()
                                .toString() + '°',
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Start,

                            )
                        Image(
                            modifier = Modifier.size(50.dp),
                            painter = painterResource(id = R.drawable._0n),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = "High: ${weatherApiResponse.main.tempMax.toInt()}° • Low: ${weatherApiResponse.main.tempMin.toInt()}°",
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {


                    Text(
                        text = weatherApiResponse.weather.first().main,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                    )

                    Text(
                        text = "Feels Like " + weatherApiResponse.main.feelsLike.toInt()
                            .toString() + '°',
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
                title = "Humidity",
                iconResourceId = R.raw.humidity,
                value = weatherApiResponse.main.humidity.toString() + '%'
            )
            MiniWeatherInfoBox(
                modifier = Modifier.weight(1f),
                title = "Pressure",
                iconResourceId = R.raw.barometer,
                value = weatherApiResponse.main.pressure.toString() + " HHmg"
            )
            MiniWeatherInfoBox(
                modifier = Modifier.weight(1f),
                title = "Wind",
                iconResourceId = R.raw.wind,
                value = weatherApiResponse.wind.speed.toInt().toString() + " M/s"
            )




        }
    }
}