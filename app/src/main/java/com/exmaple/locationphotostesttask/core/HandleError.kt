package com.exmaple.locationphotostesttask.core

import android.util.Log
import com.exmaple.locationphotostesttask.R
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface HandleError {

 suspend fun handle(e: Exception): String

 class Base @Inject constructor(
  private val managerResource: ManagerResource,
 ): HandleError {

  override suspend fun handle(e: Exception): String {
   Log.d("tag", "handle: $e")
   if(e is CloudErrorException) return e.message

   val id= when(e) {
    is CloudErrorWithoutFullDescriptionException -> e.map(Unit)
    is UnknownHostException -> R.string.no_connection_message
    else -> R.string.oops_something_went_wrong_data
   }

   return managerResource.getString(id)
  }
 }

}