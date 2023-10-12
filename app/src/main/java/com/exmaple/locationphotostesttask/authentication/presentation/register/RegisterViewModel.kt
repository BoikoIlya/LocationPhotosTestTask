package com.exmaple.locationphotostesttask.authentication.presentation.register

import androidx.lifecycle.viewModelScope
import com.exmaple.locationphotostesttask.authentication.domain.AuthenticationState
import com.exmaple.locationphotostesttask.authentication.domain.RegisterInteractor
import com.exmaple.locationphotostesttask.authentication.presentation.AuthenticationUiState
import com.exmaple.locationphotostesttask.authentication.presentation.AuthenticationViewModel
import com.exmaple.locationphotostesttask.core.DispatchersList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val dispatchersList: DispatchersList,
    private val registerStateCommunication: RegisterStateCommunication,
    private val mapper: RegisterMapper,
    private val interactor: RegisterInteractor
) : AuthenticationViewModel(registerStateCommunication) {

    fun register(
        login: String,
        password: String,
        repeatedPassword: String,
    ) = viewModelScope.launch(dispatchersList.io()) {
        registerStateCommunication.map(AuthenticationUiState.Loading)
        interactor.register(login, password, repeatedPassword).map(mapper)
    }

}