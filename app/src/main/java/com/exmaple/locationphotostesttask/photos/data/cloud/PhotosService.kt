package com.exmaple.locationphotostesttask.photos.data.cloud

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
interface PhotosService {

 companion object{
  private const val photosUrl: String = "/api/image"
  private const val deletePhotosUrl: String = "/api/image/{id}"
 }

 @GET(photosUrl)
 suspend fun fetchPhotos(
  @Header("Access-Token")
  token: String,
  @Query("page")
  pageIndex: Int
 ): Response<PhotosResponse>

 @POST(photosUrl)
 suspend fun postPhoto(
  @Header("Access-Token")
  token: String,
  @Body
  imageDtoIn: ImageDtoIn
 ):Response<PhotoPostResponseWrapper>

 @DELETE(deletePhotosUrl)
 suspend fun deletePhoto(
  @Header("Access-Token")
  token: String,
  @Path("id")
  id: Int
 ):Response<PhotoPostResponseWrapper>
}