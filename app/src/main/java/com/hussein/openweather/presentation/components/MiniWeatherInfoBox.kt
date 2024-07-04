package com.hussein.openweather.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun MiniWeatherInfoBox(
    modifier: Modifier = Modifier, title: String, iconResourceId: Int, value: Any
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(iconResourceId))
    val progress by animateLottieCompositionAsState(
        composition, iterations = 1
    )

    Card(modifier) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Normal)
            LottieAnimation(modifier = Modifier.size(80.dp),
                composition = composition,
                progress = { progress })
            Text(text = value.toString(), fontSize = 16.sp, fontWeight = FontWeight.Medium)


        }

    }
}