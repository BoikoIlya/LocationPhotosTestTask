package com.exmaple.locationphotostesttask.photodetails.presentation

import android.widget.ImageView
import android.widget.TextView
import com.exmaple.locationphotostesttask.core.ImageLoader

/**
 * Created by Ilya Boiko @camancho
on 16.10.2023.
 **/

interface PhotoDetailsUi {

 fun showToUi(
  imageView: ImageView,
  imageLoader: ImageLoader,
  dateTimeTextView: TextView
 )

 data class Base(
  private val photoUrl: String,
  private val dateAndTime: String,
 ):PhotoDetailsUi {

 override fun showToUi(
   imageView: ImageView,
   imageLoader: ImageLoader,
   dateTimeTextView: TextView
  ) {
   imageLoader.loadImage(imageView, photoUrl)
   dateTimeTextView.text = dateAndTime
  }
 }


 object Empty: PhotoDetailsUi {
  override fun showToUi(
   imageView: ImageView,
   imageLoader: ImageLoader,
   dateTimeTextView: TextView,
  ) = Unit
 }

}
