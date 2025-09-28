package com.hussein.openweather.presentation.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hussein.openweather.domain.models.Wind

@Composable
fun WindInfoWidget(modifier: Modifier = Modifier, wind: Wind) {
    val text = remember {
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 30.sp)) {
                append("${wind.speed}")
            }
            append(" ")
            withStyle(SpanStyle(fontSize = 20.sp)) {
                append("Km/H")
            }
//            pop()
        }
    }
    Card(shape = RoundedCornerShape(100),) {

        Column(
            modifier = Modifier
                .padding(48.dp)
                .fillMaxHeight()
                .then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Air, contentDescription = null)
                Text(text = "Wind", style = MaterialTheme.typography.titleSmall)

            }
            Text(text = text)
            Text(text = "From ${wind.direction}°", style = MaterialTheme.typography.bodySmall)


        }
    }

}

@Preview
@Composable
private fun WindInfoWidgetPreview() {
    WindInfoWidget(wind = Wind(15, 150))
}
