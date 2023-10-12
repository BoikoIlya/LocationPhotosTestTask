package com.exmaple.locationphotostesttask.authentication.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.exmaple.locationphotostesttask.core.Communication
import kotlinx.coroutines.flow.FlowCollector

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
abstract class AuthenticationViewModel(
 private val stateCommunication: Communication.Mutable<AuthenticationUiState>
): ViewModel() {

 suspend fun collectRegisterStateCommunication(
  lifecycleOwner: LifecycleOwner,
  flowCollector: FlowCollector<AuthenticationUiState>
 ) = stateCommunication.collect(lifecycleOwner,flowCollector)
}