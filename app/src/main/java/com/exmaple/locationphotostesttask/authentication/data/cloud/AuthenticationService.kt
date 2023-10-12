package com.exmaple.locationphotostesttask.authentication.data.cloud

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface AuthenticationService {

 companion object{
  private const val registerUrl: String = "/api/account/signup"
  private const val loginUrl: String = "/api/account/signin"
 }

 @POST(registerUrl)
 suspend fun register(
  @Body
  userDto: UserAuthenticationDto
 ): Response<AuthResponse>

 @POST(loginUrl)
 suspend fun login(
  @Body
  userDto: UserAuthenticationDto
 ): Response<AuthResponse>
}