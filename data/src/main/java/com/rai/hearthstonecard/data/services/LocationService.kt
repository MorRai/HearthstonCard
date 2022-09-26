package com.rai.hearthstonecard.data.services

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


 class LocationService @Inject constructor(context: Context) {

    private val locationClient = LocationServices.getFusedLocationProviderClient(context)

    //запрос локации постоянно
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun getLocationFlow() = callbackFlow{
        val locationRequest = LocationRequest.create().apply {
            interval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                trySend(result.lastLocation!!)//над исправить
            }
        }
       locationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())

        awaitClose {
            locationClient.removeLocationUpdates(locationCallback)
        }
    }.distinctUntilChanged { old, new ->
        old.latitude != new.latitude ||   old.longitude != new.longitude
        
    }


    // запрос локации разовов
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    suspend fun getCurrentLocation(): Location? = suspendCoroutine { cont ->
        locationClient.lastLocation
            .addOnSuccessListener {location ->
                cont.resume(location)

            }
            .addOnCanceledListener {
                cont.resume(null)
            }
            .addOnFailureListener{
                cont.resume(null)
            }

    }
}