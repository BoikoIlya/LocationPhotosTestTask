package com.exmaple.locationphotostesttask.core

import android.util.Log
import com.exmaple.locationphotostesttask.photos.data.cache.PhotoCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
interface PagingSource<T> {


 suspend fun newPage(
  cache: suspend (Int,Int)->List<T>,
  cloud: suspend (Int)->List<T>,
 ): List<T>

 suspend fun reloadPages(cache: suspend (Int,Int)->List<T>,): List<T>

 abstract class Abstract<T>(
  private val pageSize:Int
 ) : PagingSource<T>{

  private var offset = 0
  private val totalList = emptyList<T>().toMutableList()
  private var endOfData = false

  override suspend fun newPage(
   cache: suspend (Int,Int)->List<T>,
   cloud: suspend (Int)->List<T>,
  ): List<T> {
   if(endOfData) return totalList

   val cacheResult = cache.invoke(this.pageSize*this.offset,this.pageSize)

  val newPage = cacheResult.ifEmpty {
    val pageIndex = ((this.pageSize*this.offset+this.pageSize)/this.pageSize)-1
    val cloudResult = cloud.invoke(pageIndex)
    if (cloudResult.isEmpty()) {
     endOfData = true
     return totalList
    }
    cloudResult
   }

   totalList.addAll(newPage)

   val newTotalList = emptyList<T>().toMutableList()
   newTotalList.addAll(totalList)

   this.offset++
   return newTotalList
  }

  override suspend fun reloadPages(cache: suspend (Int,Int)->List<T>,): List<T> {
   val cacheResult = cache.invoke(0,this.pageSize*this.offset)

   totalList.clear()
   totalList.addAll(cacheResult)

   val newTotalList = emptyList<T>().toMutableList()
   newTotalList.addAll(cacheResult)
   return newTotalList
  }

  }

 class PhotosPagingSource @Inject constructor()
  : Abstract<PhotoCache>(pageSize),PagingSource<PhotoCache>{
  companion object{
   const val pageSize = 20
  }
 }

 }

