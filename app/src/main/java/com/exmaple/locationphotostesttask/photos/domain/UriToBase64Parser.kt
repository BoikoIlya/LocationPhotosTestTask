package com.exmaple.locationphotostesttask.photos.domain

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.FileUtils
import android.util.Base64
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
interface UriToBase64Parser {

 suspend fun parseToBase64(uri: Uri):String

 class Base @Inject constructor(
  @ApplicationContext
  private val context: Context,
  private val bitmapRotator: BitmapRotator
 ): UriToBase64Parser {

  companion object{
   private const val default_target_width = 500
   private const val default_target_height = 500
  }

  override suspend fun parseToBase64(uri: Uri):String {
   val contentResolver: ContentResolver = context.contentResolver
   val inputStream: InputStream? = contentResolver.openInputStream(uri)

   val options = BitmapFactory.Options()
   options.inJustDecodeBounds = true
   BitmapFactory.decodeStream(inputStream, null, options)
   inputStream?.close()

   val width = options.outWidth
   val height = options.outHeight
   var inSampleSize = 1

   if (width > default_target_width || height > default_target_height) {
    val halfWidth = width / 2
    val halfHeight = height / 2

    while (halfWidth / inSampleSize >= default_target_width
     && halfHeight / inSampleSize >= default_target_height) {
     inSampleSize *= 2
    }
   }

   options.inSampleSize = inSampleSize

   options.inJustDecodeBounds = false
   val scaledBitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options)

   val rotatedBitmap = bitmapRotator.rotateBitmap(scaledBitmap,uri)

   val outputStream = ByteArrayOutputStream()
   rotatedBitmap?.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

   return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
  }

 }
}