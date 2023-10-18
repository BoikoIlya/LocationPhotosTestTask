package com.exmaple.locationphotostesttask.photodetails.data.cloud

import com.exmaple.locationphotostesttask.photos.data.cloud.PhotoPostResponseWrapper
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
on 17.10.2023.
 **/
interface CommentsService {


 companion object{
  private const val commentsUrl: String = "/api/image/{imageId}/comment"
  private const val deleteCommentsUrl: String = "/api/image/{imageId}/comment/{commentId}"
 }

 @GET(commentsUrl)
 suspend fun fetchComments(
  @Header("Access-Token")
  token: String,
  @Path("imageId")
  imageId: Int,
  @Query("page")
  pageIndex: Int
 ): Response<CommentsListResponse>

 @POST(commentsUrl)
 suspend fun postComment(
  @Header("Access-Token")
  token: String,
  @Path("imageId")
  imageId: Int,
  @Body
  commentDtoIn: CommentDtoIn
 ): Response<CommentPostResponse>

 @DELETE(deleteCommentsUrl)
 suspend fun deleteComment(
  @Header("Access-Token")
  token: String,
  @Path("imageId")
  imageId: Int,
  @Path("commentId")
  commentId: Int,
 ): Response<PhotoPostResponseWrapper>
}