package com.hussein.openweather

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.hussein.openweather.data.preferences.DatastoreManager
import com.hussein.openweather.presentation.screens.Navigation
import com.hussein.openweather.presentation.screens.Screens
import com.hussein.openweather.presentation.ui.theme.OpenWeatherTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    val datastoreManager: DatastoreManager by inject()
    var onboardingState: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition {
            onboardingState == null
        }
        lifecycleScope.launch {
            val finished = datastoreManager.onBoardingCompletedState.first()
            onboardingState = finished
        }

        setContent{
            val startDestination =
                if (onboardingState == true) Screens.Main else Screens.OnboardingScreen

            OpenWeatherTheme(
                dynamicColor = true
            ) {
                Navigation(
                    onOpenAppSettings = ::openApplicationSettings,
                    openLocationSettings = ::openLocationSettings,
                    startDestination = startDestination
                )
            }
        }
    }

    private fun openApplicationSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun openLocationSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

}

