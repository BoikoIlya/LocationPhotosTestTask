package com.exmaple.locationphotostesttask.main.presentation

import com.exmaple.locationphotostesttask.core.Communication
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface AuthStateCommunication: Communication.Mutable<AuthState> {

 class Base @Inject constructor(): Communication.UiUpdate<AuthState>(AuthState.Empty),AuthStateCommunication
}