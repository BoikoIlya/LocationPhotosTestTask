package com.exmaple.locationphotostesttask.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface Communication {

 interface Update<T> {
  fun map(newValue: T)
 }

 interface SuspendUpdate<T>{
  suspend fun map(newValue: T)
 }

 interface Collector<T> {
  suspend fun collect(lifecycleOwner: LifecycleOwner, collector: FlowCollector<T>)
 }


 interface Mutable<T>: Collector<T>, Update<T>
 interface MutableSingle<T>: Collector<T>, SuspendUpdate<T>

 abstract class UiUpdate<T>(private val defaultValue: T): Mutable<T> {
  protected val stateFlow = MutableStateFlow(defaultValue)

  override fun map(newValue: T) {
   stateFlow.value = newValue
  }

  override suspend fun  collect(
   lifecycleOwner: LifecycleOwner,
   collector: FlowCollector<T>
  ) {
   stateFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    .collect(collector)
  }


 }

 abstract class SingleUiUpdate<T>: MutableSingle<T> {
  private val sharedFlow = MutableSharedFlow<T>()

  override suspend fun map(newValue: T) {
   sharedFlow.emit(newValue)
  }

  override suspend fun  collect(
   lifecycleOwner: LifecycleOwner,
   collector: FlowCollector<T>
  ) {
   sharedFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    .collect(collector)
  }
 }
}