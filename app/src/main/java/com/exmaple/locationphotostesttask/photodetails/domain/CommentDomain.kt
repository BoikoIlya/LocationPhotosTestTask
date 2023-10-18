package com.exmaple.locationphotostesttask.photodetails.domain

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.exmaple.locationphotostesttask.core.DateTimeParser
import com.exmaple.locationphotostesttask.core.Mapper
import com.exmaple.locationphotostesttask.photodetails.presentation.CommentUi
import com.exmaple.locationphotostesttask.photos.data.cache.PhotoCache

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/

data class CommentDomain(
 private val id:Int,
 private val photoId: Int,
 private val text: String,
 private val date: Long
): Mapper<DateTimeParser,CommentUi> {

 override fun map(data: DateTimeParser): CommentUi {
  val dateAndTime = data.parseToDataAndTime(date)
  return CommentUi(
   id,
   text,
   String.format("${dateAndTime.first} ${dateAndTime.second}"),
   photoId
  )
 }
}
