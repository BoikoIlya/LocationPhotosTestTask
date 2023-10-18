package com.exmaple.locationphotostesttask.photos.data

import com.exmaple.locationphotostesttask.core.PagingSource
import com.exmaple.locationphotostesttask.photos.data.cache.PhotoCache
import com.exmaple.locationphotostesttask.photos.data.cache.PhotosDao
import com.exmaple.locationphotostesttask.photos.data.cloud.CloudPhotosDataSource
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
interface PhotosRepository {

 suspend fun loadData():List<PhotoCache>

 suspend fun postPhoto(photoBase64: String,location: Pair<Double,Double>):List<PhotoCache>

 suspend fun deletePhoto(id: Int): List<PhotoCache>

 class Base @Inject constructor(
   private val pagingSource: PagingSource<PhotoCache>,
   private val service: CloudPhotosDataSource,
   private val photosDao: PhotosDao
 ): PhotosRepository {

      override suspend fun loadData(): List<PhotoCache> = pagingSource.newPage(
      cache = {offset,pageSize->
            photosDao.fetchPhotos(offset, pageSize)
       },
        cloud = {pageIndex->
            val result = service.loadData(pageIndex).mapToCache()
            photosDao.insertPhotos(result)
            result
       }
      )

     override suspend fun postPhoto(photoBase64: String, location: Pair<Double, Double>):List<PhotoCache> {
        val response = service.postPhoto(photoBase64, location)
         photosDao.insertPhoto(response.mapToCache())
         return pagingSource.reloadPages { offset, pageSize -> photosDao.fetchPhotos(offset, pageSize) }
     }

     override suspend fun deletePhoto(id: Int): List<PhotoCache> {
         service.deletePhoto(id)
         photosDao.deletePhoto(id)
         return pagingSource.reloadPages { offset, pageSize -> photosDao.fetchPhotos(offset, pageSize) }
     }
 }
}