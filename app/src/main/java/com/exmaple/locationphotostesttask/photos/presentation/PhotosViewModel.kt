package com.exmaple.locationphotostesttask.photos.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.core.ConnectionChecker
import com.exmaple.locationphotostesttask.core.DispatchersList
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventState
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventStateCommunication
import com.exmaple.locationphotostesttask.core.ManagerResource
import com.exmaple.locationphotostesttask.main.presentation.GlobalLoadingCommunication
import com.exmaple.locationphotostesttask.main.presentation.GlobalLoadingState
import com.exmaple.locationphotostesttask.photos.domain.PhotoDomainState
import com.exmaple.locationphotostesttask.photos.domain.PhotosInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
@HiltViewModel
class PhotosViewModel @Inject constructor(
 private val dispatchersList: DispatchersList,
 private val interactor: PhotosInteractor,
 private val photosListCommunication: PhotosListCommunication,
 private val photosStateCommunication: PhotosLoadStateCommunication,
 private val mapper: PhotoDomainState.Mapper,
 private val globalSingleUiEventStateCommunication: GlobalSingleUiEventStateCommunication,
 private val managerResource: ManagerResource,
 private val globalLoadingCommunication: GlobalLoadingCommunication,
 private val connectionChecker: ConnectionChecker
): ViewModel() {

 init {
     loadNewPage()
 }

 fun loadNewPage() = viewModelScope.launch(dispatchersList.io()) {
   photosStateCommunication.map(PagingLoadStateState.Loading)
   interactor.loadData().map(mapper)
 }

 fun postPhoto(result:Boolean, uri: Uri?) = viewModelScope.launch(dispatchersList.io()) {
  if (result && uri!=null) {
   globalLoadingCommunication.map(GlobalLoadingState.Loading)
   interactor.postPhoto(uri).map(mapper)
  }
 }

 fun launchDeletePhotoDialog(id:Int) = viewModelScope.launch(dispatchersList.io()) {
  globalSingleUiEventStateCommunication.map(
   GlobalSingleUiEventState.ShowDialog(DeletePhotoDialog.newInstance(id))
  )
 }

 fun deletePhoto(id: Int) = viewModelScope.launch(dispatchersList.io()) {
  interactor.deletePhoto(id).map(mapper)
 }

 fun checkConnectionAndGps(success: ()->Unit) = viewModelScope.launch(dispatchersList.io()) {
  if(!connectionChecker.isDeviceHaveConnectionAndGpsEnabled()) {
   globalSingleUiEventStateCommunication.map(
    GlobalSingleUiEventState.ShowSnackBar.Error(managerResource.getString(R.string.enable_gps_and_internet))
   )
  }else success.invoke()
 }

 suspend fun collectPhotosListCommunication(
  lifecycleOwner: LifecycleOwner,
  flowCollector: FlowCollector<List<PhotoUi>>
 ) = photosListCommunication.collect(lifecycleOwner,flowCollector)

 suspend fun collectPhotosStateCommunication(
  lifecycleOwner: LifecycleOwner,
  flowCollector: FlowCollector<PagingLoadStateState>
 ) = photosStateCommunication.collect(lifecycleOwner,flowCollector)

}