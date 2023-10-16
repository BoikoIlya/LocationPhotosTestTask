package com.exmaple.locationphotostesttask.photos.data.cloud

import com.exmaple.locationphotostesttask.authentication.data.cache.TokenStore
import com.exmaple.locationphotostesttask.core.HandleResponseData
import com.exmaple.locationphotostesttask.photos.data.cache.PhotoCache
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
interface CloudPhotosDataSource {

 suspend fun loadData(pageIndex: Int):PhotosResponse

 suspend fun postPhoto(body: ImageDtoIn):PhotoPostResponseWrapper

 suspend fun deletePhoto(id: Int)

 class Base @Inject constructor(
  private val service: PhotosService,
  private val tokenStore: TokenStore,
  private val handleResponseData: HandleResponseData
 ): CloudPhotosDataSource {

  override suspend fun loadData(pageIndex: Int): PhotosResponse = handleResponseData.handle {
   return@handle service.fetchPhotos(tokenStore.read().first(),pageIndex)
  }

  override suspend fun postPhoto(
   body: ImageDtoIn
  ): PhotoPostResponseWrapper = handleResponseData.handle {
     return@handle service.postPhoto(tokenStore.read().first(),body)
  }

  override suspend fun deletePhoto(id: Int) {
   handleResponseData.handle {
    service.deletePhoto(tokenStore.read().first(),id)
   }
  }
 }
}