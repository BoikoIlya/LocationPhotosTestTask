package com.exmaple.locationphotostesttask.photodetails.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by Ilya Boiko @camancho
on 16.10.2023.
 **/
@Dao
interface CommentsDao {

 companion object{
  const val comments_table_name = "comments_table"
 }

 @Insert
 suspend fun insertCommentsList(list: List<CommentCache>)

 @Insert
 suspend fun insertComment(comment: CommentCache)

 @Query("DELETE FROM comments_table WHERE id = :id")
 suspend fun deleteCommentById(id: Int)

 @Query("SELECT * FROM comments_table " +
         "WHERE photoId = :photoId " +
         "ORDER BY date ASC " +
         "LIMIT :limit OFFSET :offset")
 suspend fun fetchComments(offset:Int,limit: Int,photoId: Int): List<CommentCache>
}