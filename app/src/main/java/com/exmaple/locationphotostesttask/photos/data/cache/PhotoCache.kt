package com.exmaple.locationphotostesttask.photos.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.exmaple.locationphotostesttask.photos.domain.PhotoDomain

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
@Entity(tableName = PhotosDao.photos_table_name)
data class PhotoCache(
 @PrimaryKey(autoGenerate = false)
 val id: Int,
 val photoUrl: String,
 val date: Long,
 val lat: Double,
 val lng: Double,
){

  fun map() = PhotoDomain(id = id, photoUrl = photoUrl, date = date, lat = lat, lng = lng)
}
