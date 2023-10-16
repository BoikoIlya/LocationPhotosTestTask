package com.exmaple.locationphotostesttask.main.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exmaple.locationphotostesttask.core.DispatchersList
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventState
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventStateCommunication
import com.exmaple.locationphotostesttask.main.data.AuthorizationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
@HiltViewModel
class MainViewModel @Inject constructor(
 private val globalSingleUiEventStateCommunication: GlobalSingleUiEventStateCommunication,
 private val dispatchersList: DispatchersList,
 private val authorizationRepository: AuthorizationRepository,
 private val authStateCommunication: AuthStateCommunication,
 private val globalLoadingCommunication: GlobalLoadingCommunication
): ViewModel() {

 init {
     checkAuthentication()
 }

 fun checkAuthentication() = viewModelScope.launch(dispatchersList.io()){
  if(!authorizationRepository.isAuthorized())
   authStateCommunication.map(AuthState.NavigateToAuthScreen)
 }

 fun resetAuthState() = authStateCommunication.map(AuthState.Empty)

 fun sendGlobalSingleStateEvent(state: GlobalSingleUiEventState)
 = viewModelScope.launch(dispatchersList.io()) {
   globalSingleUiEventStateCommunication.map(state)
 }

 suspend fun collectGlobalSingleUiEventStateCommunication(
  lifecycleOwner: LifecycleOwner,
  flowCollector: FlowCollector<GlobalSingleUiEventState>
 ) = globalSingleUiEventStateCommunication.collect(lifecycleOwner,flowCollector)

 suspend fun collectAuthStateCommunication(
  lifecycleOwner: LifecycleOwner,
  flowCollector: FlowCollector<AuthState>
 ) = authStateCommunication.collect(lifecycleOwner,flowCollector)

 suspend fun collectGlobalLoadingCommunication(
  lifecycleOwner: LifecycleOwner,
  flowCollector: FlowCollector<GlobalLoadingState>
 ) = globalLoadingCommunication.collect(lifecycleOwner,flowCollector)
}