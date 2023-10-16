package com.exmaple.locationphotostesttask.photos.domain

import com.exmaple.locationphotostesttask.core.DateTimeParser
import com.exmaple.locationphotostesttask.core.Mapper
import com.exmaple.locationphotostesttask.photos.presentation.PhotoUi


/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
data class PhotoDomain(
 private val id: Int,
 private val photoUrl: String,
 private val date: Long,
 private val lat: Double,
 private val lng: Double,
): Mapper<DateTimeParser,PhotoUi> {

 override fun map(data: DateTimeParser): PhotoUi {
   val dateAndTime = data.parseToDataAndTime(date)
   return PhotoUi(
    id = id,
    photoUrl = photoUrl,
    date = dateAndTime.first,
    time = dateAndTime.second,
    lat = lat,
    lng = lng
   )
 }

}