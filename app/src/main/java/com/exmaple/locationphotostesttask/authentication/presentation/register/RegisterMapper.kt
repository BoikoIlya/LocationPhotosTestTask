package com.exmaple.locationphotostesttask.authentication.presentation.register

import com.exmaple.locationphotostesttask.authentication.domain.AuthenticationState
import com.exmaple.locationphotostesttask.authentication.presentation.AuthenticationMapper
import com.exmaple.locationphotostesttask.authentication.presentation.login.LoginMapper
import com.exmaple.locationphotostesttask.authentication.presentation.login.LoginStateCommunication
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventStateCommunication
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface RegisterMapper: AuthenticationState.Mapper {

 class Base @Inject constructor(
  registerStateCommunication: RegisterStateCommunication,
  globalSingleUiEventStateCommunication: GlobalSingleUiEventStateCommunication
 ): AuthenticationMapper(registerStateCommunication,globalSingleUiEventStateCommunication), RegisterMapper
}