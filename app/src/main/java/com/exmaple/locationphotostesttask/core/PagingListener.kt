package com.exmaple.locationphotostesttask.core

import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
class PagingListener(
 private val pageSize: Int,
 private val triggerCount: Int,
 private val loadPage:()->Unit,
): RecyclerView.OnScrollListener(){

 private var isLoading = false
 private var isLastPage = false
 private var isScrolling = false

 fun setLoading(isLoading: Boolean) {
  this.isLoading = isLoading
 }

 override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
  super.onScrolled(recyclerView, dx, dy)


  val layoutManager = recyclerView.layoutManager as LinearLayoutManager
  val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
  val visibleItemCount = layoutManager.childCount
  val totalItemCount = layoutManager.itemCount

  val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
  val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= (totalItemCount-triggerCount)
  val isNotAtTheBeginning = firstVisibleItemPosition >= 0
  val isTotalMoreThanVisible = totalItemCount >= pageSize

  val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtTheBeginning
          && isTotalMoreThanVisible && isScrolling

  if(shouldPaginate){
   loadPage.invoke()
   isScrolling = false
  }



 }

 override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
  super.onScrollStateChanged(recyclerView, newState)

  isScrolling = newState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE
  if(newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
   val layoutManager = recyclerView.layoutManager as LinearLayoutManager
   val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
   val visibleItemCount = layoutManager.childCount
   val totalItemCount = layoutManager.itemCount

   val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
   val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= (totalItemCount-15)
   val isNotAtTheBeginning = firstVisibleItemPosition >= 0
   val isTotalMoreThanVisible = totalItemCount >= pageSize

   val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtTheBeginning
           && isTotalMoreThanVisible

   if(shouldPaginate){
    loadPage.invoke()
    isScrolling = false
   }
  }
 }


}