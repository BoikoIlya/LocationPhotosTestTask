package com.exmaple.locationphotostesttask.authentication.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventState
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventStateCommunication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
@HiltViewModel
class MainAuthenticationViewModel @Inject constructor(
 private val globalSingleUiEventStateCommunication: GlobalSingleUiEventStateCommunication
): ViewModel() {

 suspend fun collectGlobalSingleUiEventStateCommunication(
  lifecycleOwner: LifecycleOwner,
  flowCollector: FlowCollector<GlobalSingleUiEventState>
 ) = globalSingleUiEventStateCommunication.collect(lifecycleOwner,flowCollector)
}