package com.exmaple.locationphotostesttask.authentication.data.cloud

import com.google.gson.annotations.SerializedName

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
data class AuthData(
 @SerializedName("userId")
 val userId:Int,
 @SerializedName("login")
 val login:String,
 @SerializedName("token")
 val token:String
)
