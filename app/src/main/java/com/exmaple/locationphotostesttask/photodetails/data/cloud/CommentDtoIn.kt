package com.exmaple.locationphotostesttask.photodetails.data.cloud

import com.google.gson.annotations.SerializedName

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
data class CommentDtoIn(
 @SerializedName("text")
 private val text: String
)