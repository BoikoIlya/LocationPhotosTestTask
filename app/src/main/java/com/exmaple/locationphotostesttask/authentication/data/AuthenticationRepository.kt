package com.exmaple.locationphotostesttask.authentication.data

import com.exmaple.locationphotostesttask.authentication.data.cloud.AuthenticationService
import com.exmaple.locationphotostesttask.authentication.data.cloud.UserAuthenticationDto
import com.exmaple.locationphotostesttask.authentication.data.cache.TokenStore
import com.exmaple.locationphotostesttask.core.HandleResponseData
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface AuthenticationRepository {

 suspend fun register(login: String, password: String)

 suspend fun login(login: String, password: String)

 class Base @Inject constructor(
  private val service: AuthenticationService,
  private val tokenStore: TokenStore,
  private val handleResponseData: HandleResponseData
 ): AuthenticationRepository{

  override suspend fun register(login: String, password: String)
   = handleResponseData.handle {
   service.register(UserAuthenticationDto(login, password))
  }.saveToken(tokenStore)

  override suspend fun login(login: String, password: String)
   = handleResponseData.handle {
   service.login(UserAuthenticationDto(login, password))
  }.saveToken(tokenStore)

 }
}