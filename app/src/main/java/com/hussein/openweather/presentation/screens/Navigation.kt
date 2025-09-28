package com.hussein.openweather.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.hussein.openweather.presentation.screens.main.MainScreen
import com.hussein.openweather.presentation.screens.onboarding.OnboardingScreen
import com.hussein.openweather.presentation.screens.onboarding.OnboardingViewModel
import com.hussein.openweather.presentation.screens.onboarding.map_screen.MapScreen
import com.hussein.openweather.presentation.screens.settings.SettingScreenActions
import com.hussein.openweather.presentation.screens.settings.SettingsScreen
import com.hussein.openweather.presentation.screens.settings.SettingsViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel


@Serializable
sealed class Screens {
    @Serializable
    data object OnboardingScreen : Screens()

    @Serializable
    data object Main : Screens()

    @Serializable
    data object Settings : Screens()

    @Serializable
    data object MapScreen : Screens()
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Navigation(
    onOpenAppSettings: () -> Unit,
    openLocationSettings: () -> Unit,
    startDestination: Screens
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Main
    ) {

        composable<Screens.OnboardingScreen> {
            val viewmodel = koinViewModel<OnboardingViewModel>()
            val providerState = viewmodel.locationProviderFlow.collectAsStateWithLifecycle(false)
            OnboardingScreen(onPermissionDenied = {}, onPermissionGranted = {
                viewmodel.saveLocation()
            }, isProviderEnabled = providerState.value)

        }

        composable<Screens.Main> {
            MainScreen(navController = navController)
        }


        composable<Screens.Settings> {

            val viewModel = koinViewModel<SettingsViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            SettingsScreen(
                state = state,
                onOpenLocationSettings = openLocationSettings,
                onNavigateBack = { navController.navigate(Screens.Main) },
                updateLocation = { viewModel.updateLocation() },
                onTempUnitChange = {
                }, onWindUnitChange = {
                }, onDynamicColorChange = {
                    viewModel.onAction(SettingScreenActions.ToggleDynamicColor(it))
                }, onToggleAutoUpdateLocation = {
                    viewModel.onAction(SettingScreenActions.ToggleAutoUpdate(it))
                }, requestPermissions = {

                }

            )
        }
    }


}