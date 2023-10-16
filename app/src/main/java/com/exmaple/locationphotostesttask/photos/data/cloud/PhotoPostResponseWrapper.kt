package com.exmaple.locationphotostesttask.photos.data.cloud

import com.google.gson.annotations.SerializedName

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
data class PhotoPostResponseWrapper(
 @SerializedName("status")
 private val status: Int,
 @SerializedName("data")
 private val data: PhotoPostResponse
){

 fun mapToCache() = data.mapToCache()

}