package com.hussein.openweather.ext

import android.content.Context
import android.location.LocationManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

fun Context.toast(message: String) {
    Toast.makeText(this , message , Toast.LENGTH_SHORT).show()
}
fun Context.isProviderEnabled(): Boolean{
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(
        LocationManager.GPS_PROVIDER
    ) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

val Context.appPreferences: DataStore<Preferences> by preferencesDataStore("AppPreferences")


fun <T1, T2, T3, T4, R> combineAsState(
    scope: CoroutineScope,
    started: SharingStarted,
    initialValue: R,
    f1: Flow<T1>,
    f2: Flow<T2>,
    f3: Flow<T3>,
    f4: Flow<T4>,
    mapper: (T1, T2, T3, T4) -> R,
): StateFlow<R> = combine(f1, f2, f3, f4) {
    @Suppress("UNCHECKED_CAST")
    mapper(it[0] as T1, it[1] as T2, it[2] as T3, it[3] as T4)
}.stateIn(
    scope = scope,
    started = started,
    initialValue = initialValue,
)