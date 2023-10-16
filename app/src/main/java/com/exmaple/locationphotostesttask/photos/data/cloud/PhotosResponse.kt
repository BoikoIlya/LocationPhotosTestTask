package com.exmaple.locationphotostesttask.photos.data.cloud

import com.google.gson.annotations.SerializedName

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
data class PhotosResponse(
 @SerializedName("status")
 private val status: Int,
 @SerializedName("data")
 private val data: List<PhotoResponseItem>
){

 fun mapToCache() = data.map { it.map() }

}
