package com.hussein.openweather.presentation.screens.onboarding.map_screen

import android.location.Geocoder
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberMarkerState
import com.hussein.openweather.data.preferences.UserLocation

@Composable
fun MapScreen(onLocationSelected: (UserLocation) -> Unit) {

    val context = LocalContext.current
    var location by remember { mutableStateOf<LatLng?>(null) }
    val addressName = remember(location) {
        location?.let {
            Geocoder(context).getFromLocation(
                location?.latitude ?: 0.0,
                location?.longitude ?: 0.0,
                1
            )
                ?.get(0)
                ?.let { "${it.subAdminArea}, ${it.countryName}" } ?: "Selected address"
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                location?.let {
                    onLocationSelected(
                        UserLocation(
                            latitude = it.latitude,
                            longitude = it.longitude,
                            addressName = addressName ?: ""
                        )
                    )
                }
            }) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
            }
        }) { padding ->
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            onMapClick = { location = it }) {
            location?.let {
                MarkerComposable(state = rememberMarkerState(position = it)) {
                    Surface(
                        modifier = Modifier.wrapContentSize(),
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.8f)
                    ) {
                        Text(
                            "$addressName",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }

                }
            }
        }

    }
}