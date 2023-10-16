package com.exmaple.locationphotostesttask.photos.data.cloud

import com.exmaple.locationphotostesttask.photos.data.cache.PhotoCache
import com.google.gson.annotations.SerializedName

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
data class PhotoPostResponse(
 @SerializedName("id")
 private val id: Int,
 @SerializedName("url")
 private val url: String,
 @SerializedName("date")
 private val date: Long,
 @SerializedName("lat")
 private val lat: Double,
 @SerializedName("lng")
 private val lng: Double,
){

 fun mapToCache() = PhotoCache(id,url,date,lat, lng)
}
