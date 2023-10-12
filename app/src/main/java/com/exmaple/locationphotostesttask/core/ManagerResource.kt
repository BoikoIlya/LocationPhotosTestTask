package com.exmaple.locationphotostesttask.core

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface ManagerResource {

 fun getString(@StringRes id: Int): String

 class Base @Inject constructor(
  @ApplicationContext
  private val context: Context
 ): ManagerResource {

  override fun getString(id: Int) = context.getString(id)

 }

}