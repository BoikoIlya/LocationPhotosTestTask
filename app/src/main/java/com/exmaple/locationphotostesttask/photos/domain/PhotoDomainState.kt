package com.exmaple.locationphotostesttask.photos.domain

import android.net.Uri

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
sealed interface PhotoDomainState{

 suspend fun map(mapper: Mapper)


  interface Mapper{
   suspend fun map(state:Success, photos: List<PhotoDomain>)
   suspend fun map(state: Success.SuccessPhotoAction, photos: List<PhotoDomain>, message: String)
   suspend fun map(state:Failure, message: String)
   suspend fun map(state:EnableGpsAndNetwork, uri: Uri)
   suspend fun map(state:PhotoActionFailure, message: String)
  }

 open class Success(
  private val photos: List<PhotoDomain>
 ): PhotoDomainState {
  override suspend fun map(mapper: Mapper) = mapper.map(this,photos)

 data class SuccessPhotoAction (
  private val photos: List<PhotoDomain>,
  private val message: String
 ): Success(photos){
  override suspend fun map(mapper: Mapper) = mapper.map(this,photos,message)
 }
 }

 data class Failure(
  private val message: String
 ): PhotoDomainState {
  override suspend fun map(mapper: Mapper) = mapper.map(this,message)
 }

 data class PhotoActionFailure(
  private val message: String
 ): PhotoDomainState {
  override suspend fun map(mapper: Mapper) = mapper.map(this,message)
 }

 data class EnableGpsAndNetwork(
  private val photoUri: Uri
 ): PhotoDomainState {
  override suspend fun map(mapper: Mapper) = mapper.map(this,photoUri)
 }

}