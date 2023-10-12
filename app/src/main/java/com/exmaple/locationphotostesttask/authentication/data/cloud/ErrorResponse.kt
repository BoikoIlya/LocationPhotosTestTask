package com.exmaple.locationphotostesttask.authentication.data.cloud

import com.exmaple.locationphotostesttask.core.CloudErrorWithoutFullDescriptionException
import com.exmaple.locationphotostesttask.core.Mapper
import com.google.gson.annotations.SerializedName

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
data class ErrorResponse(
 @SerializedName("status")
private val status: Int,
 @SerializedName("error")
private val error: String,
 @SerializedName("valid")
private val valid: List<ValidationError>?=null
): Mapper<Unit,String>{


 override fun map(data: Unit): String {
  return valid?.joinToString { String.format(it.field + " " + it.message) }
   ?: throw CloudErrorWithoutFullDescriptionException(error)
 }
}