package com.hussein.openweather.presentation.screens.settings.componenets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Air
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hussein.openweather.domain.SpeedUnits
import com.hussein.openweather.domain.TemperatureUnits

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitSettings(
    modifier: Modifier = Modifier,
    selectedTempUnit: TemperatureUnits,
    selectedWindSpeedUnits: SpeedUnits,
    onTempUnitChange: (TemperatureUnits) -> Unit,
    onWindUnitChange: (SpeedUnits) -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        shape = RoundedCornerShape(12.dp)
    ) {


        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OptionsDropDownMenu(
                options = TemperatureUnits.entries.map { it.name }.toList(),
                onItemSelected = {
                    onTempUnitChange(TemperatureUnits.valueOf(it))
                },
                selectedItem = selectedTempUnit.name,
                label = "Temperature",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AcUnit,
                        contentDescription = ""
                    )
                }
            )

            OptionsDropDownMenu(
                options = SpeedUnits.entries.map { it.name }.toList(),
                onItemSelected = {
                    onWindUnitChange(SpeedUnits.valueOf(it))
                },
                selectedItem = selectedWindSpeedUnits.name,
                label = "Speed",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Air,
                        contentDescription = ""
                    )
                }
            )

        }
    }

}