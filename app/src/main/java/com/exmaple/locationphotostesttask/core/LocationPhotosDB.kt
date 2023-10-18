package com.exmaple.locationphotostesttask.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.exmaple.locationphotostesttask.photodetails.data.cache.CommentCache
import com.exmaple.locationphotostesttask.photodetails.data.cache.CommentsDao
import com.exmaple.locationphotostesttask.photos.data.cache.PhotoCache
import com.exmaple.locationphotostesttask.photos.data.cache.PhotosDao

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
@Database(
 entities = [PhotoCache::class,CommentCache::class],
 version = 1,
 exportSchema = false
)
abstract class LocationPhotosDB: RoomDatabase() {

 abstract fun getPhotosDao(): PhotosDao
 abstract fun getCommentsDao(): CommentsDao
}