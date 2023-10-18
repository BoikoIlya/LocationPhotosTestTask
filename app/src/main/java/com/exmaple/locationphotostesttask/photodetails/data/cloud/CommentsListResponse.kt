package com.exmaple.locationphotostesttask.photodetails.data.cloud

import com.google.gson.annotations.SerializedName

data class CommentsListResponse(
    @SerializedName("data")
    private val data: List<CommentResponse>,
    @SerializedName("status")
    private val status: Int
){

    fun mapToCache(photoId:Int) = data.map { it.mapToCache(photoId) }

}