package com.exmaple.locationphotostesttask.photos.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 15.10.2023.
 **/
interface BitmapRotator {

 suspend fun rotateBitmap(source: Bitmap?, photoUri: Uri): Bitmap?

 class Base @Inject constructor(
  @ApplicationContext
  private val context: Context
 ): BitmapRotator{

  override suspend fun rotateBitmap(source: Bitmap?, photoUri: Uri,): Bitmap? {
   if(source==null) return null

   val cursor = context.contentResolver.query(photoUri, null, null, null, null)
   val path = cursor?.use {
    it.moveToFirst()
    val columnIndex = it.getColumnIndex(MediaStore.Images.Media.DATA)
    it.getString(columnIndex)
   }

   val exifInterface = ExifInterface(File(path!!))
   val angle =
    when(exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED)) {
    ExifInterface.ORIENTATION_ROTATE_90 -> 90f
    ExifInterface.ORIENTATION_ROTATE_180 -> 180f
    ExifInterface.ORIENTATION_ROTATE_270 -> 270f
    else -> return source
   }

   val matrix = Matrix()
   matrix.postRotate(angle)
   return Bitmap.createBitmap(source,0,0,source.width,source.height,matrix,true)
  }
  }

 }
