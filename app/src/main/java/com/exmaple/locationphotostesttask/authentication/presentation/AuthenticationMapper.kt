package com.exmaple.locationphotostesttask.authentication.presentation

import com.exmaple.locationphotostesttask.authentication.domain.AuthenticationState
import com.exmaple.locationphotostesttask.core.Communication
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventState
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventStateCommunication

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
abstract class AuthenticationMapper(
    private val registerStateCommunication: Communication.Mutable<AuthenticationUiState>,
    private val globalSingleUiEventStateCommunication: GlobalSingleUiEventStateCommunication
): AuthenticationState.Mapper {

    override suspend fun map(state: AuthenticationState.Success) {
        registerStateCommunication.map(AuthenticationUiState.Success)
    }

    override suspend fun map(state: AuthenticationState.Failure, message: String) {
        registerStateCommunication.map(AuthenticationUiState.Failure)
        globalSingleUiEventStateCommunication.map(
            GlobalSingleUiEventState.ShowSnackBar.Error(message)
        )
    }

}