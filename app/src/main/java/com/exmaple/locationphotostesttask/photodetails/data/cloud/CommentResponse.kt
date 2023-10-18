package com.exmaple.locationphotostesttask.photodetails.data.cloud

import com.exmaple.locationphotostesttask.photodetails.data.cache.CommentCache
import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("date")
    private val date: Long,
    @SerializedName("id")
    private val id: Int,
    @SerializedName("text")
    private val text: String
){

    fun mapToCache(photoId:Int) = CommentCache(id,photoId,text,date)

}