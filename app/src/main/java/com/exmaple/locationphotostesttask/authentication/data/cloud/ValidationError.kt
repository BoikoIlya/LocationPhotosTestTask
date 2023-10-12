package com.exmaple.locationphotostesttask.authentication.data.cloud

import com.google.gson.annotations.SerializedName

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
data class ValidationError(
 @SerializedName("field")
 val field: String,
 @SerializedName("message")
 val message: String
)