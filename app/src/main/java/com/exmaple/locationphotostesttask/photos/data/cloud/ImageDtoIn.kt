package com.exmaple.locationphotostesttask.photos.data.cloud

import com.google.gson.annotations.SerializedName

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
data class ImageDtoIn(
 @SerializedName("base64Image")
 val base64Image: String,
 @SerializedName("date")
 val date: Long,
 @SerializedName("lat")
 val lat: Double,
 @SerializedName("lng")
 val lng: Double,
)
