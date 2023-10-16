package com.exmaple.locationphotostesttask.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/

interface DispatchersList {

 fun io(): CoroutineDispatcher
 fun default(): CoroutineDispatcher
 fun ui(): CoroutineDispatcher

 class Base @Inject constructor() : DispatchersList {
  override fun io(): CoroutineDispatcher = Dispatchers.IO
  override fun default(): CoroutineDispatcher = Dispatchers.Default
  override fun ui(): CoroutineDispatcher = Dispatchers.Main
 }
}