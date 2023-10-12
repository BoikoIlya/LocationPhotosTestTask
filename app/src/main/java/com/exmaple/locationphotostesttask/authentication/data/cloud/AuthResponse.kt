package com.exmaple.locationphotostesttask.authentication.data.cloud

import androidx.work.Data
import com.exmaple.locationphotostesttask.authentication.data.cache.TokenStore
import com.exmaple.locationphotostesttask.core.CloudErrorException
import com.google.gson.annotations.SerializedName

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
data class AuthResponse(
 @SerializedName("status")
 private val status: Int,
 @SerializedName("data")
 private val data: AuthData,
 @SerializedName("error")
 private val error: String
){

// init {
//
// }
//
// fun checkForError() {
//  if(error!=null) return
// }

 suspend fun saveToken(data: TokenStore) = data.save(this.data!!.token)


}
