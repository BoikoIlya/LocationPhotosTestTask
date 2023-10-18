package com.exmaple.locationphotostesttask.photos.presentation

import com.exmaple.locationphotostesttask.core.Communication
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
interface PhotosLoadStateCommunication: Communication.Mutable<PagingLoadState> {

    class Base @Inject constructor(): PhotosLoadStateCommunication,
        Communication.UiUpdate<PagingLoadState>(PagingLoadState.Empty)
}