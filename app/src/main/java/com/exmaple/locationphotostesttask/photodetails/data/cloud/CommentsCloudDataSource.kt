package com.exmaple.locationphotostesttask.photodetails.data.cloud

import com.exmaple.locationphotostesttask.authentication.data.cache.TokenStore
import com.exmaple.locationphotostesttask.core.HandleResponseData
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
interface CommentsCloudDataSource {

 suspend fun fetchComments(photoId: Int, pageIndex: Int): CommentsListResponse

 suspend fun postComment(photoId: Int, text: String): CommentPostResponse

 suspend fun deleteComment(photoId: Int, commentId: Int)

 class Base @Inject constructor(
  private val service: CommentsService,
  private val tokenStore: TokenStore,
  private val handleResponseData: HandleResponseData
 ): CommentsCloudDataSource {

  override suspend fun fetchComments(photoId: Int, pageIndex: Int): CommentsListResponse
   = handleResponseData.handle {
     service.fetchComments(
      tokenStore.read().first(),
      photoId,
      pageIndex
     )
  }

  override suspend fun postComment(photoId: Int, text: String): CommentPostResponse
   = handleResponseData.handle {
    service.postComment(
     tokenStore.read().first(),
     photoId,
     CommentDtoIn(text)
    )
  }

  override suspend fun deleteComment(photoId: Int, commentId: Int) {
   handleResponseData.handle {
    service.deleteComment(
     tokenStore.read().first(),
     photoId,
     commentId
    )
   }
  }
 }
}