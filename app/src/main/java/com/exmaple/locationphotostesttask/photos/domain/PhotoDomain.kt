package com.exmaple.locationphotostesttask.photos.domain

import com.exmaple.locationphotostesttask.core.DateTimeParser
import com.exmaple.locationphotostesttask.core.Mapper
import com.exmaple.locationphotostesttask.photodetails.presentation.PhotoDetailsUi
import com.exmaple.locationphotostesttask.photos.presentation.PhotoUi
import javax.inject.Inject


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
) {

    fun <T> map(mapper: Mapper<T>) = mapper.map(id, photoUrl, date, lat, lng)

    interface Mapper<T> {

        fun map(
            id: Int,
            photoUrl: String,
            date: Long,
            lat: Double,
            lng: Double,
        ): T

    }


    class ToPhotoUiMapper @Inject constructor(
     private val dateTimeParser: DateTimeParser,
    ) : Mapper<PhotoUi> {

        override fun map(id: Int, photoUrl: String, date: Long, lat: Double, lng: Double): PhotoUi {
            val dateAndTime = dateTimeParser.parseToDataAndTime(date)
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

    class ToPhotoDetailsUiMapper @Inject constructor(
     private val dateTimeParser: DateTimeParser,
    ) : Mapper<PhotoDetailsUi> {

        override fun map(
         id: Int,
         photoUrl: String,
         date: Long,
         lat: Double,
         lng: Double,
        ): PhotoDetailsUi {
            val dateAndTime = dateTimeParser.parseToDataAndTime(date)
            return PhotoDetailsUi.Base(
                photoUrl = photoUrl,
                dateAndTime = String.format("${dateAndTime.first} ${dateAndTime.second}"),
            )
        }
    }


}