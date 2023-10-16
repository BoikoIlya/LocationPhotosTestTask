package com.exmaple.locationphotostesttask.core

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.exmaple.locationphotostesttask.R
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
interface ImageLoader {

 fun loadImage(target:ImageView,url: String)

 class Base @Inject constructor(): ImageLoader {

  override fun loadImage(target: ImageView, url: String) {
   Glide.with(target)
    .load(url)
    .transition(DrawableTransitionOptions.withCrossFade())
    .into(target)
  }
 }
}