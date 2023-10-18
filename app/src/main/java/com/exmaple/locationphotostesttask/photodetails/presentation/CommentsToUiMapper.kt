package com.exmaple.locationphotostesttask.photodetails.presentation

import android.util.Log
import com.exmaple.locationphotostesttask.core.DateTimeParser
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventState
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventStateCommunication
import com.exmaple.locationphotostesttask.photodetails.domain.CommentDomain
import com.exmaple.locationphotostesttask.photodetails.domain.PhotoDetailsDomainState
import com.exmaple.locationphotostesttask.photos.presentation.PagingLoadState
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
class CommentsToUiMapper @Inject constructor(
    private val сommentSectionStateCommunication: CommentSectionStateCommunication,
    private val commentsListCommunication: CommentsListCommunication,
    private val commentsLoadStateCommunication: CommentsLoadStateCommunication,
    private val dateTimeParser: DateTimeParser,
    private val singleUiEventStateCommunication: GlobalSingleUiEventStateCommunication
) : PhotoDetailsDomainState.Mapper {

    override suspend fun map(
        state: PhotoDetailsDomainState.Success,
        comments: List<CommentDomain>,
    ) {
        Log.d("tag", "map: ${comments.size} ")
        commentsLoadStateCommunication.map(PagingLoadState.Hide)
        commentsListCommunication.map(comments.map { it.map(dateTimeParser) })
    }

    override suspend fun map(state: PhotoDetailsDomainState.Failure, message: String) {
        commentsLoadStateCommunication.map(PagingLoadState.Failure(message))
    }

    override suspend fun map(
        state: PhotoDetailsDomainState.Failure.CommentPostingFailure,
        message: String,
    ) {
        сommentSectionStateCommunication.map(CommentSectionState.DisableLoading.Failure(message))
    }

    override suspend fun map(
        state: PhotoDetailsDomainState.Success.SuccessPosting,
        comments: List<CommentDomain>,
    ) {
        commentsLoadStateCommunication.map(PagingLoadState.Hide)
        commentsListCommunication.map(comments.map { it.map(dateTimeParser) })
        сommentSectionStateCommunication.map(CommentSectionState.DisableLoading.NotReadyToSend.SuccessPosting)
    }

    override suspend fun map(
        state: PhotoDetailsDomainState.Failure.CommentDeletingFailure,
        message: String,
    ) {
        singleUiEventStateCommunication.map(GlobalSingleUiEventState.ShowSnackBar.Error(message))
    }
}