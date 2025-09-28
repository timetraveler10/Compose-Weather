package com.hussein.openweather.presentation.screens.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hussein.openweather.presentation.screens.main.components.CurrentWeatherInfo
import com.hussein.openweather.presentation.screens.main.components.DailyWeatherInfoCard
import com.hussein.openweather.presentation.screens.main.components.HourlyWeatherCard
import com.hussein.openweather.presentation.screens.main.components.WindInfoWidget
import com.hussein.openweather.presentation.util.ErrorScreen
import com.hussein.openweather.presentation.util.LoadingScreen
import com.hussein.openweather.presentation.screens.Screens
import com.hussein.openweather.presentation.ui.theme.DURATION
import com.hussein.openweather.presentation.ui.theme.DURATION_ENTER
import com.hussein.openweather.presentation.ui.theme.DURATION_EXIT_SHORT
import com.hussein.openweather.presentation.ui.theme.EmphasizedAccelerateEasing
import com.hussein.openweather.presentation.ui.theme.EmphasizedDecelerateEasing
import com.hussein.openweather.presentation.ui.theme.EmphasizedEasing
import org.koin.androidx.compose.koinViewModel
val LocalAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }
@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = koinViewModel(),
    navController: NavController
) {


    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val snackBarState = remember { SnackbarHostState() }
    val state by mainViewModel.state.collectAsStateWithLifecycle()

    when {

        state.data != null -> {

            var selectedDailyWeatherIndex by remember { mutableIntStateOf(0) }
            var shouldShowDetails by remember { mutableStateOf(false) }

            SharedTransitionLayout {
                CompositionLocalProvider(LocalSharedTransitionScope provides this) {

                    AnimatedContent(
                        targetState = shouldShowDetails,
                        label = "",
                        transitionSpec = {
                            fadeIn(
                                tween(
                                    durationMillis = DURATION_ENTER,
                                    delayMillis = DURATION_EXIT_SHORT,
                                    easing = EmphasizedDecelerateEasing
                                )
                            ) togetherWith fadeOut(
                                tween(
                                    durationMillis = DURATION_EXIT_SHORT,
                                    easing = EmphasizedAccelerateEasing
                                )
                            ) using SizeTransform { _, _ ->
                                tween(durationMillis = DURATION, easing = EmphasizedEasing)
                            }
                        }
                    ) { showDetails ->
                        CompositionLocalProvider(LocalAnimatedVisibilityScope provides this) {

                            if (showDetails) {
                                DetailsScreen(
                                    daily = state.data!!.daily,
                                    onBackPress = { shouldShowDetails = !shouldShowDetails },
                                    selectedDailyWeatherIndex = selectedDailyWeatherIndex
                                )

                            } else {
                                Scaffold(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                                    snackbarHost = { SnackbarHost(hostState = snackBarState) },
                                    topBar = {
                                        LargeTopAppBar(
                                            title = {
                                                Column(horizontalAlignment = Alignment.Start) {
                                                    Text(text = state.data!!.current.weatherCondition.text)
                                                    Text(
                                                        text = "Feels Like " + state.data!!.current.feelsLike
                                                            .toString() + '°',
                                                        style = MaterialTheme.typography.titleSmall

                                                    )
                                                }
                                            },
                                            scrollBehavior = scrollBehavior,
                                            actions = {
                                                FilledTonalIconButton(
                                                    onClick = { mainViewModel.onEvent(MainScreenActions.RefreshMainScreen) }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Refresh,
                                                        contentDescription = null
                                                    )
                                                }

                                                FilledTonalIconButton(
                                                    onClick = { navController.navigate(Screens.Settings) }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Settings,
                                                        contentDescription = null
                                                    )
                                                }
                                            })

                                    },
                                ) { innerPadding ->
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(innerPadding)
                                            .padding(horizontal = 16.dp)
                                            .verticalScroll(rememberScrollState()),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {

                                        /*
                                        * need refactoring asap
                                        * */
                                        CurrentWeatherInfo(current = state.data!!.current)

                                        HourlyWeatherCard(data = state.data!!.hourly,)

                                        DailyWeatherInfoCard(
                                            dailyWeather = state.data!!.daily,
                                            onItemClick = {
                                                selectedDailyWeatherIndex = it
                                                shouldShowDetails = !shouldShowDetails
                                            })

                                        WindInfoWidget(wind = state.data!!.current.wind)
                                    }
                                }
                            }
                        }
                    }
                }


            }
        }

        state.loading -> {
            LoadingScreen()
        }

        state.error != null -> {

            ErrorScreen {
                Text(
                    text = state.error!!.message!!,
                    textAlign = TextAlign.Center,
                    softWrap = true,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

    }


}

