package com.exmaple.locationphotostesttask.photodetails.data.cloud

import com.google.gson.annotations.SerializedName

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
data class CommentPostResponse(
 @SerializedName("data")
 private val data: CommentResponse,
 @SerializedName("status")
 private val status: Int
){

 fun mapToCache(photoId: Int) = data.mapToCache(photoId)

}
