package com.exmaple.locationphotostesttask.photos.domain

import android.net.Uri
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.core.EnableGpsAndNetworkException
import com.exmaple.locationphotostesttask.core.HandleResponse
import com.exmaple.locationphotostesttask.core.ManagerResource
import com.exmaple.locationphotostesttask.photos.data.PhotosRepository
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
interface PhotosInteractor {

 suspend fun loadData(): PhotoDomainState

 suspend fun postPhoto(photoUri: Uri):PhotoDomainState

 suspend fun deletePhoto(id: Int):PhotoDomainState

 class Base @Inject constructor(
  private val handleResponse: HandleResponse,
  private val repository: PhotosRepository,
  private val managerResource: ManagerResource,
  private val parser: UriToBase64Parser,
  private val locationProvider: LocationProvider,
 ): PhotosInteractor {

  override suspend fun loadData():PhotoDomainState = handleResponse.handle({

   val result = repository.loadData()

   return@handle if(result.isEmpty())
    PhotoDomainState.Failure(managerResource.getString(R.string.no_photos))
   else
    PhotoDomainState.Success(result.map { it.map() })

  },{message,_->
    return@handle PhotoDomainState.Failure(message)
  })

  override suspend fun postPhoto(photoUri: Uri):PhotoDomainState = handleResponse.handle({
     val base64Img = parser.parseToBase64(photoUri)
     val location = locationProvider.getCurrentLatLng()
     val reloadedList =  repository.postPhoto(base64Img,location)
     return@handle PhotoDomainState.Success.SuccessPhotoAction(
     reloadedList.map { it.map() },
      managerResource.getString(R.string.photo_posted))
  },{message,e->
   return@handle if(e is EnableGpsAndNetworkException || e is UnknownHostException)
                    PhotoDomainState.EnableGpsAndNetwork(photoUri)
                 else
                     PhotoDomainState.PhotoActionFailure(message)
  })

  override suspend fun deletePhoto(id: Int) :PhotoDomainState = handleResponse.handle({
      val reloadedList = repository.deletePhoto(id)

      return@handle PhotoDomainState.Success.SuccessPhotoAction(
          reloadedList.map { it.map() },
          managerResource.getString(R.string.photo_deleted))
     },{message,_->
         return@handle PhotoDomainState.PhotoActionFailure(message)
     })
 }
}