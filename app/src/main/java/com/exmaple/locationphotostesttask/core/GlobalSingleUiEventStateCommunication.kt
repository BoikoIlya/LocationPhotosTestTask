package com.exmaple.locationphotostesttask.core

import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface GlobalSingleUiEventStateCommunication: Communication.MutableSingle<GlobalSingleUiEventState> {

    class Base @Inject constructor(): GlobalSingleUiEventStateCommunication,
        Communication.SingleUiUpdate<GlobalSingleUiEventState>()
}