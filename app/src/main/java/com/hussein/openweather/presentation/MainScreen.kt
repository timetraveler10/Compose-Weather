package com.hussein.openweather.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hussein.openweather.R
import com.hussein.openweather.dummy_weather_data
import com.hussein.openweather.ui.theme.components.ErrorScreen
import com.hussein.openweather.ui.theme.components.LoadingScreen
import com.hussein.openweather.ui.theme.components.MiniWeatherInfoBox
import com.hussein.openweather.ui.theme.components.SlidingText
import com.hussein.openweather.ui.theme.components.WeatherInfoScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(uiState: WeatherUIState) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var topAppBarTitle by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .padding(), topBar = {

        LargeTopAppBar(

            title = { SlidingText(text = topAppBarTitle) }, scrollBehavior = scrollBehavior
        )

    }) { innerPadding ->


        AnimatedContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            targetState = uiState, label = "Main content"
        ) {

            when {

                it.error != null -> {
                    topAppBarTitle = "Error"

                    ErrorScreen(errorMsg = uiState.error!!)
                }

                it.data != null -> {
                    topAppBarTitle = "Weather in " + uiState.data!!.name
                    WeatherInfoScreen(weatherApiResponse = uiState.data)
                }

                it.loading -> {
                    topAppBarTitle = "Loading..."
                    LoadingScreen()


                }
            }
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(uiState = WeatherUIState(data = dummy_weather_data, loading = false))
}