package com.hussein.openweather.presentation.screens.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope.PlaceHolderSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.hussein.openweather.domain.models.DailyWeather
import com.hussein.openweather.presentation.screens.main.components.DetailsScreenContent
import com.hussein.openweather.presentation.ui.theme.DURATION
import com.hussein.openweather.presentation.ui.theme.DURATION_ENTER
import com.hussein.openweather.presentation.ui.theme.DURATION_EXIT_SHORT
import com.hussein.openweather.presentation.ui.theme.EmphasizedAccelerateEasing
import com.hussein.openweather.presentation.ui.theme.EmphasizedDecelerateEasing
import com.hussein.openweather.presentation.ui.theme.EmphasizedEasing


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    daily: List<DailyWeather>,
    onBackPress: () -> Unit,
    selectedDailyWeatherIndex: Int,
) {

    BackHandler {
        onBackPress()
    }
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current!!
    val sharedTransitionScope = LocalSharedTransitionScope.current!!

    var selectedDailyWeatherIndex by remember { mutableIntStateOf(selectedDailyWeatherIndex) }
    val selectedDailyWeather by remember { derivedStateOf { daily[selectedDailyWeatherIndex] } }

    with(sharedTransitionScope) {
        with(animatedVisibilityScope) {
            Scaffold(
                modifier = Modifier,
                topBar = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ), title = {
                            Text(
                                modifier = Modifier
                                    .sharedBounds(
                                        rememberSharedContentState(
                                            key = "title"
                                        ), animatedVisibilityScope = animatedVisibilityScope
                                    ),
                                text = "Daily Forecast",
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        navigationIcon = {
                            FilledTonalIconButton(
                                modifier = Modifier
                                    .renderInSharedTransitionScopeOverlay(zIndexInOverlay = -1f)
                                    .animateEnterExit(
                                        enter = scaleIn(
                                            animationSpec = tween(
                                                durationMillis = DURATION_ENTER,
                                                delayMillis = DURATION_EXIT_SHORT,
                                                easing = EmphasizedDecelerateEasing
                                            )
                                        ) + fadeIn(
                                            animationSpec = tween(
                                                DURATION_ENTER,
                                                delayMillis = DURATION_EXIT_SHORT,
                                                easing = EmphasizedDecelerateEasing
                                            )
                                        ), exit = scaleOut(
                                            animationSpec = tween(
                                                durationMillis = DURATION_EXIT_SHORT,
                                                delayMillis = 0,
                                                easing = EmphasizedAccelerateEasing
                                            )
                                        ) + fadeOut(
                                            animationSpec = tween(
                                                durationMillis = DURATION_EXIT_SHORT,
                                                delayMillis = 0,
                                                easing = EmphasizedAccelerateEasing
                                            )
                                        )
                                    ),
                                onClick = onBackPress
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = null
                                )
                            }
                        })


                },
                containerColor = MaterialTheme.colorScheme.background
            ) { innerPadding ->

                DetailsScreenContent(
                    cardModifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .sharedBounds(
                            boundsTransform = { _, _ ->
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
                            sharedContentState = rememberSharedContentState(
                                key = "container"
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                            placeHolderSize = PlaceHolderSize.animatedSize
                        ),
                    lazyRowModifier = Modifier

                        .sharedElement(
                            boundsTransform = { initial, target ->
                                tween(durationMillis = DURATION, easing = EmphasizedEasing)
                            },
                            sharedContentState = rememberSharedContentState(key = "lazyList"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            placeHolderSize = PlaceHolderSize.animatedSize,
                        )
                        .fillMaxWidth(),
                    innerColumnModifier = Modifier.animateEnterExit(
                        enter = fadeIn(
                            animationSpec = tween(
                                1000
                            )
                        )
                    ),
                    dailyWeather = daily,
                    onWeatherItemClick = { selectedDailyWeatherIndex = it },
                    selectedDailyWeather = selectedDailyWeather
                )

            }

        }

    }

}


