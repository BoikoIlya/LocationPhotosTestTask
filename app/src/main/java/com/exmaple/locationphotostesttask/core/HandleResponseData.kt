package com.exmaple.locationphotostesttask.core

import com.exmaple.locationphotostesttask.authentication.data.cloud.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface HandleResponseData {

  suspend fun<T> handle(block: suspend ()->Response<T>): T

  class Base @Inject constructor(): HandleResponseData{

    override suspend fun <T> handle(block: suspend () -> Response<T>):T {
          val response = block.invoke()
          if(response.isSuccessful && response.body()!=null){
            return response.body()!!
          }else {
              val jsonResponse = response.errorBody()?.string()
              val gson = Gson()
              val errorResponse = gson.fromJson(jsonResponse, ErrorResponse::class.java)
              throw CloudErrorException(message = errorResponse.map(Unit))
          }
    }

  }
}