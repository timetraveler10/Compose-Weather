package com.hussein.openweather.presentation.screens.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.maps.android.compose.rememberCameraPositionState
import com.hussein.openweather.presentation.screens.settings.componenets.LocationSettings
import com.hussein.openweather.domain.SpeedUnits
import com.hussein.openweather.domain.TemperatureUnits
import com.hussein.openweather.presentation.screens.settings.componenets.DynamicThemeOptions
import com.hussein.openweather.presentation.screens.settings.componenets.SettingsSuggestionCard
import com.hussein.openweather.presentation.screens.settings.componenets.UnitSettings


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onOpenLocationSettings: () -> Unit,
    updateLocation: () -> Unit,
    onTempUnitChange: (TemperatureUnits) -> Unit,
    onWindUnitChange: (SpeedUnits) -> Unit,
    onDynamicColorChange: (Boolean) -> Unit,
    onToggleAutoUpdateLocation: (Boolean) -> Unit,
    requestPermissions:()-> Unit,
    state: SettingsScreenState
) {

    val mapCameraPositionState = rememberCameraPositionState()

    @Composable
    fun SettingsScreenSectionHeader(title: String) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

    }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {

                    CenterAlignedTopAppBar(
                        title = { Text("Settings") },
                        navigationIcon = {
                            FilledTonalIconButton(
                                modifier = Modifier,
                                onClick = onNavigateBack
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = null)
                            }
                        })
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {

                AnimatedVisibility(state.locationProviderEnabled , enter = expandVertically()) {
                    SettingsSuggestionCard(
                        leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "") },
                        title = "Gps is not enabled.",
                        description = "Enabled gps for accurate weather forecast", trailingContent = {
                            IconButton(onClick = onOpenLocationSettings) {
                                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                            }
                        }
                    )
                }
                SettingsScreenSectionHeader(title = "Location")
                LocationSettings(
                    modifier = Modifier.fillMaxWidth(),
                    cameraPosition = mapCameraPositionState,
                    userLocation = state.userLocation,
                    autoUpdate = state.autoUpdate,
                    onClick = requestPermissions,
                    onAutoUpdateSwitchChange = onToggleAutoUpdateLocation,

                )

                SettingsScreenSectionHeader("Theme")
                DynamicThemeOptions(
                    isEnabled = state.dynamicThemeEnabled,
                    onCheckedChange = onDynamicColorChange
                )

            }

        }
    }






