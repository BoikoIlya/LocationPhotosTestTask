package com.exmaple.locationphotostesttask.main.data

import com.exmaple.locationphotostesttask.authentication.data.cache.TokenStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface AuthorizationRepository {

 suspend fun isAuthorized(): Boolean

 suspend fun logout()

 class Base @Inject constructor(
  private val tokenStore: TokenStore
 ): AuthorizationRepository {

  override suspend fun isAuthorized(): Boolean = tokenStore.read().first().isNotEmpty()
  override suspend fun logout() = tokenStore.save("")


 }
}