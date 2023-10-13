package com.exmaple.locationphotostesttask.authentication.presentation.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.exmaple.locationphotostesttask.authentication.data.cache.TokenStore
import com.exmaple.locationphotostesttask.authentication.domain.AuthenticationState
import com.exmaple.locationphotostesttask.authentication.domain.LoginInteractor
import com.exmaple.locationphotostesttask.authentication.presentation.AuthenticationViewModel
import com.exmaple.locationphotostesttask.authentication.presentation.AuthenticationUiState
import com.exmaple.locationphotostesttask.core.DispatchersList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
@HiltViewModel
class LoginViewModel @Inject constructor(
 private val dispatchersList: DispatchersList,
 private val loginStateCommunication: LoginStateCommunication,
 private val mapper: LoginMapper,
 private val interactor: LoginInteractor
) : AuthenticationViewModel(loginStateCommunication) {


  fun login(
      login: String,
      password: String,
    ) = viewModelScope.launch(dispatchersList.io()) {
      loginStateCommunication.map(AuthenticationUiState.Loading)
      interactor.login(login, password).map(mapper)
  }

}