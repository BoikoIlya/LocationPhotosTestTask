package com.exmaple.locationphotostesttask.core

import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface HandleResponse {

 suspend fun <T> handle(
  block: suspend () -> T,
  error: suspend (String, Exception)-> T,
 ): T

 class Base @Inject constructor(
  private val handleError: HandleError,
 ): HandleResponse{


  override suspend fun <T> handle(
   block: suspend () -> T,
   error: suspend (String, Exception) -> T,
  ): T =
   try {
    val result = block.invoke()
    result
   }catch (e: Exception){
    error.invoke(handleError.handle(e),e)
   }


 }

}