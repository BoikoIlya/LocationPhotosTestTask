package com.exmaple.locationphotostesttask.photodetails.domain

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
sealed interface PhotoDetailsDomainState{

 suspend fun map(mapper: Mapper)


  interface Mapper{
   suspend fun map(state:Success, comments: List<CommentDomain>)
   suspend fun map(state:Failure, message: String)
   suspend fun map(state:Failure.CommentPostingFailure, message: String)
   suspend fun map(state: Success.SuccessPosting,comments: List<CommentDomain>)
   suspend fun map(state: Failure.CommentDeletingFailure,message: String)
  }

 open class Success(
  private val comments: List<CommentDomain>
 ): PhotoDetailsDomainState {
  override suspend fun map(mapper: Mapper) = mapper.map(this,comments)

  data class SuccessPosting(
   private val comments: List<CommentDomain>
  ): Success(comments){

   override suspend fun map(mapper: Mapper) = mapper.map(this,comments)
  }

 }

 data class Failure(
  private val message: String
 ): PhotoDetailsDomainState {
  override suspend fun map(mapper: Mapper) = mapper.map(this, message)


  data class CommentPostingFailure(
   private val message: String
  ) : PhotoDetailsDomainState {
   override suspend fun map(mapper: Mapper) = mapper.map(this, message)
  }

  data class CommentDeletingFailure(
   private val message: String
  ) : PhotoDetailsDomainState {
   override suspend fun map(mapper: Mapper) = mapper.map(this, message)
  }
 }


}