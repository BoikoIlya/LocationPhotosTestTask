package com.exmaple.locationphotostesttask.photodetails.domain

import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.core.HandleResponse
import com.exmaple.locationphotostesttask.core.ManagerResource
import com.exmaple.locationphotostesttask.photodetails.data.PhotoDetailsRepository
import com.exmaple.locationphotostesttask.photos.domain.PhotoDomain
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
interface PhotoDetailsInteractor {

    suspend fun fetchComments(photoId: Int): PhotoDetailsDomainState

    suspend fun fetchPhotoById(id: Int): PhotoDomain

    suspend fun postComment(photoId: Int,text: String): PhotoDetailsDomainState

    suspend fun deleteComment(photoId: Int,id: Int): PhotoDetailsDomainState

    class Base @Inject constructor(
        private val handleResponse: HandleResponse,
        private val repository: PhotoDetailsRepository,
        private val managerResource: ManagerResource
    ): PhotoDetailsInteractor {

        override suspend fun fetchComments(photoId: Int): PhotoDetailsDomainState =
            handleResponse.handle({
               val result = repository.fetchComments(photoId)
                if(result.isEmpty())
                    PhotoDetailsDomainState.Failure(managerResource.getString(R.string.no_comments))
                else
                    PhotoDetailsDomainState.Success(result.map { it.map(Unit) })
            },{message,_->
                PhotoDetailsDomainState.Failure(message)
            })

        override suspend fun fetchPhotoById(id: Int): PhotoDomain = repository.fetchPhotoById(id).map()

        override suspend fun postComment(photoId: Int, text: String): PhotoDetailsDomainState =
            handleResponse.handle({
                val result = repository.postComment(photoId, text.trim())
                PhotoDetailsDomainState.Success.SuccessPosting(result.map { it.map(Unit) })
            },{message,_->
                PhotoDetailsDomainState.Failure.CommentPostingFailure(message)
            })

        override suspend fun deleteComment(photoId: Int,id: Int): PhotoDetailsDomainState =
            handleResponse.handle({
                val result = repository.deleteComment(id, photoId)
                PhotoDetailsDomainState.Success(result.map { it.map(Unit) })
            },{message,_->
                PhotoDetailsDomainState.Failure.CommentDeletingFailure(message)
            })
    }
}