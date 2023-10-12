package com.exmaple.locationphotostesttask.authentication.domain

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
sealed interface AuthenticationState{

 suspend fun map(mapper: Mapper)

 interface Mapper{
   suspend fun map(state: Success)
   suspend fun map(state: Failure, message: String)
 }

 object Success:AuthenticationState {
  override suspend fun map(mapper: Mapper) = mapper.map(this)
 }

 data class Failure(private val message: String): AuthenticationState{
  override suspend fun map(mapper: Mapper) = mapper.map(this,message)
 }

 object Empty: AuthenticationState {
  override suspend fun map(mapper: Mapper) = Unit
 }

}