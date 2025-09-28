package com.hussein.openweather.presentation.screens.settings.componenets


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.ktx.model.cameraPosition
import com.hussein.openweather.data.preferences.UserLocation


@Composable
fun LocationSettings(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    userLocation: UserLocation?,
    cameraPosition: CameraPositionState,
    autoUpdate: Boolean,
    onAutoUpdateSwitchChange: (Boolean) -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {

        userLocation?.let {
            LaunchedEffect(it) {
                cameraPosition.move(
                    update = CameraUpdateFactory.newLatLngZoom(
                        it.toLatLng(), 14f
                    )
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(it.addressName, style = MaterialTheme.typography.titleMedium)

                MapCard(cameraPosition, userLocation)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Auto update location", style = MaterialTheme.typography.titleMedium)
                    //todo tell user if permission is denied or p-denied
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = autoUpdate,
                        onCheckedChange = onAutoUpdateSwitchChange,
                        enabled = true
                    )
                }


            }


        }


    }

}

@Composable
fun MapCard(cameraPosition: CameraPositionState, userLocation: UserLocation) {

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(8.dp)),
        cameraPositionState = cameraPosition,
        properties = MapProperties(
            isMyLocationEnabled = false,
            mapType = MapType.TERRAIN
        ),
        uiSettings = MapUiSettings(
            myLocationButtonEnabled = false,
            zoomControlsEnabled = false,
            mapToolbarEnabled = false,
            tiltGesturesEnabled = false,
            rotationGesturesEnabled = false,
            zoomGesturesEnabled = false,
            scrollGesturesEnabled = false,
        ),
    ) {
        Circle(
            LatLng(userLocation.latitude, userLocation.longitude),
            radius = 800.0,
            fillColor = Color.Cyan.copy(alpha = 0.2f),
            strokeColor = Color.Transparent
        )
    }
}
