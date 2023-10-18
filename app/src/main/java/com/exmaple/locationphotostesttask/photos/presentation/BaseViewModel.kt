package com.exmaple.locationphotostesttask.photos.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.exmaple.locationphotostesttask.core.Communication
import kotlinx.coroutines.flow.FlowCollector

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
abstract class BaseViewModel<T>(
 private val communicationDataList: Communication.Collector<List<T>>,
 private val loadStateCommunication: Communication.Collector<PagingLoadState>
): ViewModel() {

 suspend fun collectDataListCommunication(
  lifecycleOwner: LifecycleOwner,
  flowCollector: FlowCollector<List<T>>
 ) = communicationDataList.collect(lifecycleOwner,flowCollector)

 suspend fun collectLoadStateCommunication(
  lifecycleOwner: LifecycleOwner,
  flowCollector: FlowCollector<PagingLoadState>
 ) = loadStateCommunication.collect(lifecycleOwner,flowCollector)

}