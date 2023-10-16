package com.exmaple.locationphotostesttask.core

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 15.10.2023.
 **/
interface ConnectionChecker {

 suspend fun isDeviceHaveConnectionAndGpsEnabled(): Boolean

 class Base @Inject constructor(
  @ApplicationContext
  private val context: Context
 ) : ConnectionChecker{

  private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

  override suspend fun isDeviceHaveConnectionAndGpsEnabled(): Boolean {

   val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
   val networkConnection = manager.getNetworkCapabilities(manager.activeNetwork)?.let {
    it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
   } ?: false

   val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

   return networkConnection && isGpsEnabled

  }
 }
}