package com.hussein.openweather.presentation.screens.main.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SharedTransitionScope.PlaceHolderSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hussein.openweather.R
import com.hussein.openweather.domain.models.DailyWeather
import com.hussein.openweather.presentation.ui.theme.DURATION
import com.hussein.openweather.presentation.ui.theme.DURATION_ENTER
import com.hussein.openweather.presentation.ui.theme.DURATION_EXIT_SHORT
import com.hussein.openweather.presentation.ui.theme.EmphasizedAccelerateEasing
import com.hussein.openweather.presentation.ui.theme.EmphasizedDecelerateEasing
import com.hussein.openweather.presentation.ui.theme.EmphasizedEasing
import com.hussein.openweather.utils.parseDate

context(AnimatedVisibilityScope, SharedTransitionScope)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DailyWeatherInfoCard(
    dailyWeather: List<DailyWeather>,
    onItemClick: (Int) -> Unit,
) {

    Card(
        modifier = Modifier.sharedBounds(

            boundsTransform = { initial, target ->
                tween(durationMillis = DURATION, easing = EmphasizedEasing)
            },
            enter = fadeIn(
                tween(
                    durationMillis = DURATION_ENTER,
                    delayMillis = DURATION_EXIT_SHORT,
                    easing = EmphasizedDecelerateEasing
                )
            ),
            exit = fadeOut(
                tween(
                    durationMillis = DURATION_EXIT_SHORT,
                    easing = EmphasizedAccelerateEasing
                )
            ),
            sharedContentState = rememberSharedContentState(key = "container"),
            animatedVisibilityScope = this@AnimatedVisibilityScope,
            placeHolderSize = PlaceHolderSize.animatedSize,
        ), shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null)
                Text(
                    modifier = Modifier.sharedBounds(
                        rememberSharedContentState(
                            key = "title"
                        ), this@AnimatedVisibilityScope
                    ),
                    text = "Daily Forecast",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        boundsTransform = { _, _ ->
                            tween(durationMillis = DURATION, easing = EmphasizedEasing)
                        },
                        sharedContentState = rememberSharedContentState(key = "lazyList"),
                        animatedVisibilityScope = this@AnimatedVisibilityScope,
                        placeHolderSize = PlaceHolderSize.animatedSize,

                        ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(dailyWeather) { index, data ->

                    DailyWeatherListItem(data = data, onItemClick = {
                        onItemClick(index)
                    })

                }
            }

        }
    }

}


@Composable
fun DailyWeatherListItem(
    data: DailyWeather,
    onItemClick: () -> Unit,
) {
    Surface(shape = RoundedCornerShape(50), onClick = onItemClick , color = MaterialTheme.colorScheme.surfaceContainerHigh) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Text(
                    text = "${data.maxTemp}°",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "${data.minTemp}°",
                    style = MaterialTheme.typography.labelLarge,
                )
            }

            Icon(
                modifier = Modifier
                    .size(32.dp),
                painter = painterResource(id = R.drawable.clear_n),
                contentDescription = null
            )

            Text(
                text = parseDate(
                    date = data.date,
                ).take(3),
                style = MaterialTheme.typography.titleSmall,
                overflow = TextOverflow.Ellipsis,
            )


        }
    }

}
