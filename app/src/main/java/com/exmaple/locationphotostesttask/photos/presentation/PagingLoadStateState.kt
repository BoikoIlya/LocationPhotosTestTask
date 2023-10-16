package com.exmaple.locationphotostesttask.photos.presentation

import com.exmaple.locationphotostesttask.core.PagingListener

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
sealed interface PagingLoadStateState {

    fun apply(loadStateAdapter: LoadStateAdapter,pagingLister: PagingListener)

    object Loading: PagingLoadStateState {
        override fun apply(loadStateAdapter: LoadStateAdapter,pagingLister: PagingListener) {
            loadStateAdapter.showState(listOf(""))
            pagingLister.setLoading(true)
        }
    }

    object Hide: PagingLoadStateState {
        override fun apply(loadStateAdapter: LoadStateAdapter,pagingLister: PagingListener) {
            loadStateAdapter.showState(emptyList())
            pagingLister.setLoading(false)
        }
    }

    data class Failure(
        private val message: String
    ): PagingLoadStateState {
        override fun apply(loadStateAdapter: LoadStateAdapter,pagingLister: PagingListener) {
            loadStateAdapter.showState(listOf(message))
        }
    }

    object Empty: PagingLoadStateState {
        override fun apply(loadStateAdapter: LoadStateAdapter,pagingLister: PagingListener) = Unit
    }

}
