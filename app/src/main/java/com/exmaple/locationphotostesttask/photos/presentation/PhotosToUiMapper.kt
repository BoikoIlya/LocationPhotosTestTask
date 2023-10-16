package com.exmaple.locationphotostesttask.photos.presentation

import android.net.Uri
import com.exmaple.locationphotostesttask.core.DateTimeParser
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventState
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventStateCommunication
import com.exmaple.locationphotostesttask.main.presentation.GlobalLoadingCommunication
import com.exmaple.locationphotostesttask.main.presentation.GlobalLoadingState
import com.exmaple.locationphotostesttask.photos.domain.PhotoDomain
import com.exmaple.locationphotostesttask.photos.domain.PhotoDomainState
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
class PhotosToUiMapper @Inject constructor(
     private val photosListCommunication: PhotosListCommunication,
     private val photosStateCommunication: PhotosLoadStateCommunication,
     private val dateTimeParser: DateTimeParser,
     private val globalSingleUiEventStateCommunication: GlobalSingleUiEventStateCommunication,
     private val globalLoadingCommunication: GlobalLoadingCommunication
): PhotoDomainState.Mapper {

 override suspend fun map(state: PhotoDomainState.Success, photos: List<PhotoDomain>) {
   photosListCommunication.map(photos.map { it.map(dateTimeParser) })
   photosStateCommunication.map(PagingLoadStateState.Hide)
 }

    override suspend fun map(
        state: PhotoDomainState.Success.SuccessPhotoAction,
        photos: List<PhotoDomain>,
        message: String
    ) {
        photosListCommunication.map(photos.map { it.map(dateTimeParser) })
        globalSingleUiEventStateCommunication.map(GlobalSingleUiEventState.ShowSnackBar.Success(message))
        globalLoadingCommunication.map(GlobalLoadingState.Dismiss)
    }

    override suspend fun map(state: PhotoDomainState.Failure, message: String) {
        photosStateCommunication.map(PagingLoadStateState.Failure(message))
        globalLoadingCommunication.map(GlobalLoadingState.Dismiss)
 }

    override suspend fun map(state: PhotoDomainState.EnableGpsAndNetwork, uri:Uri) {
        globalLoadingCommunication.map(GlobalLoadingState.Dismiss)
        globalSingleUiEventStateCommunication.map(
            GlobalSingleUiEventState.ShowDialog(EnableGpsAndRetryFragment.newInstance(uri))
        )
    }

    override suspend fun map(state: PhotoDomainState.PhotoActionFailure, message: String) {
        globalLoadingCommunication.map(GlobalLoadingState.Dismiss)
        globalSingleUiEventStateCommunication.map(GlobalSingleUiEventState.ShowSnackBar.Error(message))
    }
}