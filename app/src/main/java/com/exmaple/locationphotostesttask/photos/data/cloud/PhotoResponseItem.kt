package com.exmaple.locationphotostesttask.photos.data.cloud

import com.exmaple.locationphotostesttask.photos.data.cache.PhotoCache
import com.google.gson.annotations.SerializedName

data class PhotoResponseItem(
   @SerializedName("date")
   private val date: Long,
   @SerializedName("id")
   private val id: Int,
   @SerializedName("lat")
   private val lat: Double,
   @SerializedName("lng")
   private val lng: Double,
   @SerializedName("url")
   private val url: String
){

   fun map() = PhotoCache(id = id, photoUrl = url, date = date.toLong(), lat = lat, lng =lng)

}