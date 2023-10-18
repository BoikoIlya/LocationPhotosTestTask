package com.exmaple.locationphotostesttask.photodetails.presentation

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.exmaple.locationphotostesttask.core.DispatchersList
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventState
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventStateCommunication
import com.exmaple.locationphotostesttask.photodetails.domain.PhotoDetailsDomainState
import com.exmaple.locationphotostesttask.photodetails.domain.PhotoDetailsInteractor
import com.exmaple.locationphotostesttask.photos.domain.PhotoDomain
import com.exmaple.locationphotostesttask.photos.presentation.BaseViewModel
import com.exmaple.locationphotostesttask.photos.presentation.PagingLoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sin

/**
 * Created by Ilya Boiko @camancho
on 16.10.2023.
 **/
@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val dispatchersList: DispatchersList,
    private val photoDetailsCommunication: PhotoDetailsCommunication,
    private val commentsListCommunication: CommentsListCommunication,
    private val loadStateCommunication: CommentsLoadStateCommunication,
    private val commentSectionStateCommunication: CommentSectionStateCommunication,
    private val interactor: PhotoDetailsInteractor,
    private val mapper: PhotoDetailsDomainState.Mapper,
    private val toPhotoDetailsUiMapper: PhotoDomain.Mapper<PhotoDetailsUi>,
): BaseViewModel<CommentUi>(commentsListCommunication,loadStateCommunication) {

    fun fetchPhotoDetails(photoId: Int) = viewModelScope.launch(dispatchersList.io()) {
        val result = interactor.fetchPhotoById(photoId)
        photoDetailsCommunication.map(result.map(toPhotoDetailsUiMapper))
    }

    fun fetchComments(photoId: Int) = viewModelScope.launch(dispatchersList.io()) {
        loadStateCommunication.map(PagingLoadState.Loading)
        interactor.fetchComments(photoId).map(mapper)
    }

    fun postComment(text: String,photoId: Int) = viewModelScope.launch(dispatchersList.io()) {
        commentSectionStateCommunication.map(CommentSectionState.Loading)
        interactor.postComment(photoId, text).map(mapper)
    }

    fun deleteComment(photoId: Int,id: Int) = viewModelScope.launch(dispatchersList.io()) {
        interactor.deleteComment(photoId, id).map(mapper)
    }


    fun commentSectionValidation(text: String){
        if(text.trim().isNotEmpty())
            commentSectionStateCommunication.map(CommentSectionState.DisableLoading.ReadyToSend)
        else
            commentSectionStateCommunication.map(CommentSectionState.DisableLoading.NotReadyToSend())
    }

    suspend fun collectCommentSectionStateCommunication(
        lifecycleOwner: LifecycleOwner,
        flowCollector: FlowCollector<CommentSectionState>
    ) = commentSectionStateCommunication.collect(lifecycleOwner,flowCollector)

    suspend fun collectPhotoDetailsCommunication(
        lifecycleOwner: LifecycleOwner,
        flowCollector: FlowCollector<PhotoDetailsUi>
    ) = photoDetailsCommunication.collect(lifecycleOwner,flowCollector)


}