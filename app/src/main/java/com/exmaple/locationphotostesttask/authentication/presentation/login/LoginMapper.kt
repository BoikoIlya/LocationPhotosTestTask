package com.exmaple.locationphotostesttask.authentication.presentation.login

import com.exmaple.locationphotostesttask.authentication.domain.AuthenticationState
import com.exmaple.locationphotostesttask.authentication.presentation.AuthenticationMapper
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventStateCommunication
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface LoginMapper: AuthenticationState.Mapper {

 class Base @Inject constructor(
  loginStateCommunication: LoginStateCommunication,
  globalSingleUiEventStateCommunication: GlobalSingleUiEventStateCommunication
 ): AuthenticationMapper(loginStateCommunication,globalSingleUiEventStateCommunication),LoginMapper
}