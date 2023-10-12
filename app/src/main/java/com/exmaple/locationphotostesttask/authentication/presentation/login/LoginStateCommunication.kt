package com.exmaple.locationphotostesttask.authentication.presentation.login

import com.exmaple.locationphotostesttask.authentication.presentation.AuthenticationUiState
import com.exmaple.locationphotostesttask.core.Communication
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface LoginStateCommunication: Communication.Mutable<AuthenticationUiState> {

    class Base @Inject constructor(): LoginStateCommunication,
        Communication.UiUpdate<AuthenticationUiState>(AuthenticationUiState.Empty)
}