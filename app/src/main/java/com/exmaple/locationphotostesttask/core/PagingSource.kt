package com.exmaple.locationphotostesttask.core

import android.util.Log
import com.exmaple.locationphotostesttask.photodetails.data.cache.CommentCache
import com.exmaple.locationphotostesttask.photos.data.cache.PhotoCache
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
interface PagingSource<T> {


    suspend fun newPage(
        cache: suspend (Int, Int) -> List<T>,
        cloud: suspend (Int) -> List<T>,
    ): List<T>

    suspend fun reloadPages(cache: suspend (Int, Int) -> List<T>): List<T>

    abstract class Abstract<T>(
     protected val pageSize: Int,
    ) : PagingSource<T> {

        protected var offset = 0
        protected val totalList = emptyList<T>().toMutableList()
        private var endOfData = false

        override suspend fun newPage(
            cache: suspend (Int, Int) -> List<T>,
            cloud: suspend (Int) -> List<T>,
        ): List<T> {
            if (endOfData) return totalList

            val cacheResult = cache.invoke(this.pageSize * this.offset, this.pageSize)

            val newPage = cacheResult.ifEmpty {
                val pageIndex = ((this.pageSize * this.offset + this.pageSize) / this.pageSize) - 1
                val cloudResult = cloud.invoke(pageIndex)
                if (cloudResult.isEmpty()) {
                    endOfData = true
                    return totalList
                }
                cloudResult
            }

            addToTotalList(newPage)

            val newTotalList = emptyList<T>().toMutableList()
            newTotalList.addAll(totalList)


            this.offset++
            return newTotalList
        }

        override suspend fun reloadPages(cache: suspend (Int, Int) -> List<T>): List<T> {
            val pageSize = this.pageSize * this.offset
            val cacheResult = cache.invoke(
                0,
                if(offset==0) pageSize+this.pageSize else pageSize)

            totalList.clear()
            totalList.addAll(cacheResult)

            val newTotalList = emptyList<T>().toMutableList()
            newTotalList.addAll(cacheResult)
            return newTotalList
        }

        open fun addToTotalList(newPage: List<T>) {
            totalList.addAll(newPage)
        }


    }

    class PhotosPagingSource @Inject constructor() : Abstract<PhotoCache>(pageSize),
        PagingSource<PhotoCache> {
        companion object {
            const val pageSize = 20
        }
    }

    class CommentsPagingSource @Inject constructor() : Abstract<CommentCache>(pageSize),
        PagingSource<CommentCache> {
        companion object {
            const val pageSize = 20
        }


        override fun addToTotalList(newPage: List<CommentCache>) {
            val newList = emptyList<CommentCache>().toMutableList()
            newList.addAll(newPage.reversed())
            newList.addAll(totalList)
            totalList.clear()
            totalList.addAll(newList)
        }

        override suspend fun reloadPages(cache: suspend (Int, Int) -> List<CommentCache>): List<CommentCache> {
            val pageSize = this.pageSize * this.offset
            val cacheResult = cache.invoke(
                0,
                if(offset==0) pageSize+this.pageSize else pageSize)

            totalList.clear()
            totalList.addAll(cacheResult.reversed())

            val newTotalList = emptyList<CommentCache>().toMutableList()
            newTotalList.addAll(totalList)

            return newTotalList
        }
    }

}

