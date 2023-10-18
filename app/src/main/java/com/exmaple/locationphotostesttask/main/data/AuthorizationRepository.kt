package com.exmaple.locationphotostesttask.main.data

import com.bumptech.glide.Glide
import com.exmaple.locationphotostesttask.authentication.data.cache.TokenStore
import com.exmaple.locationphotostesttask.core.LocationPhotosDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface AuthorizationRepository {

 suspend fun isAuthorized(): Flow<Boolean>

 suspend fun logout()

 class Base @Inject constructor(
  private val tokenStore: TokenStore,
  private val db: LocationPhotosDB,
 ): AuthorizationRepository {

  override suspend fun isAuthorized(): Flow<Boolean> = tokenStore.read().map { it.isNotEmpty() }
  override suspend fun logout() {
   db.clearAllTables()
   tokenStore.save("")
  }


 }
}