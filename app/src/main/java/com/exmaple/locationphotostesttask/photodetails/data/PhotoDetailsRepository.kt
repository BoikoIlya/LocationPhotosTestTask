package com.exmaple.locationphotostesttask.photodetails.data

import android.util.Log
import com.exmaple.locationphotostesttask.core.PagingSource
import com.exmaple.locationphotostesttask.photodetails.data.cache.CommentCache
import com.exmaple.locationphotostesttask.photodetails.data.cache.CommentsDao
import com.exmaple.locationphotostesttask.photodetails.data.cloud.CommentsCloudDataSource
import com.exmaple.locationphotostesttask.photos.data.cache.PhotoCache
import com.exmaple.locationphotostesttask.photos.data.cache.PhotosDao
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
interface PhotoDetailsRepository {

    suspend fun fetchComments(photoId: Int): List<CommentCache>

    suspend fun fetchPhotoById(id: Int): PhotoCache

    suspend fun postComment(photoId: Int, text: String): List<CommentCache>

    suspend fun deleteComment(id: Int, photoId: Int): List<CommentCache>


    class Base @Inject constructor(
        private val pagingSource: PagingSource<CommentCache>,
        private val commentsDao: CommentsDao,
        private val photosDao: PhotosDao,
        private val cloudDataSource: CommentsCloudDataSource,
    ) : PhotoDetailsRepository {


        override suspend fun fetchComments(photoId: Int): List<CommentCache> =
            pagingSource.newPage(
                cache = { offset, pageSize ->
                    commentsDao.fetchComments(offset, pageSize, photoId)
                },
                cloud = { pageIndex ->
                    val response = cloudDataSource.fetchComments(photoId, pageIndex)
                    val comments = response.mapToCache(photoId)
                    commentsDao.insertCommentsList(comments)
                    comments
                })

        override suspend fun fetchPhotoById(id: Int): PhotoCache =
            photosDao.fetchPhotoById(id)

        override suspend fun postComment(photoId: Int, text: String): List<CommentCache> {
            val cloudResponse = cloudDataSource.postComment(photoId, text)
            commentsDao.insertComment(cloudResponse.mapToCache(photoId))
            return pagingSource.reloadPages { offset, pageSize ->
                commentsDao.fetchComments(offset, pageSize, photoId)

            }
        }

        override suspend fun deleteComment(id: Int, photoId: Int): List<CommentCache> {
            cloudDataSource.deleteComment(photoId, id)
            commentsDao.deleteCommentById(id)
            return pagingSource.reloadPages { offset, pageSize ->
                commentsDao.fetchComments(offset, pageSize, photoId)
            }
        }


    }
}