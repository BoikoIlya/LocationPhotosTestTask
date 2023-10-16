package com.exmaple.locationphotostesttask.main.presentation

import com.exmaple.locationphotostesttask.core.Communication
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
interface GlobalLoadingCommunication: Communication.Mutable<GlobalLoadingState> {

    class Base @Inject constructor(): GlobalLoadingCommunication
        ,Communication.UiUpdate<GlobalLoadingState>(GlobalLoadingState.Empty)
}