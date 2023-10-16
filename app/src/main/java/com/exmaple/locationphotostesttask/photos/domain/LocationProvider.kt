package com.exmaple.locationphotostesttask.photos.domain

import android.annotation.SuppressLint
import android.util.Log
import com.exmaple.locationphotostesttask.core.EnableGpsAndNetworkException
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.CompletableDeferred
import kotlin.math.log

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
interface LocationProvider {

 suspend fun getCurrentLatLng(): Pair<Double,Double>

 class Base(
  private val fusedLocationProviderClient: FusedLocationProviderClient
 ): LocationProvider {

  @SuppressLint("MissingPermission")
  override suspend fun getCurrentLatLng(): Pair<Double, Double> {
      val deferred = CompletableDeferred<Pair<Double, Double>>()

      fusedLocationProviderClient.lastLocation
          .addOnSuccessListener { location ->
          if (location != null) {
              deferred.complete(Pair(location.latitude, location.longitude))
          } else {
              deferred.completeExceptionally(EnableGpsAndNetworkException())
          }
      }
      return deferred.await()
      }
 }
}