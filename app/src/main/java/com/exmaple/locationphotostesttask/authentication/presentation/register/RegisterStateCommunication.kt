package com.exmaple.locationphotostesttask.authentication.presentation.register

import com.exmaple.locationphotostesttask.authentication.presentation.AuthenticationUiState
import com.exmaple.locationphotostesttask.core.Communication
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface RegisterStateCommunication: Communication.Mutable<AuthenticationUiState> {

    class Base @Inject constructor(): RegisterStateCommunication,
        Communication.UiUpdate<AuthenticationUiState>(AuthenticationUiState.Empty)
}