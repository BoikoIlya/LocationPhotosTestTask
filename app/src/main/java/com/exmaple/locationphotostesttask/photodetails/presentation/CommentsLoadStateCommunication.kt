package com.exmaple.locationphotostesttask.photodetails.presentation

import com.exmaple.locationphotostesttask.core.Communication
import com.exmaple.locationphotostesttask.photos.presentation.PagingLoadState
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
interface CommentsLoadStateCommunication: Communication.Mutable<PagingLoadState> {

    class Base @Inject constructor(): CommentsLoadStateCommunication,
        Communication.UiUpdate<PagingLoadState>(PagingLoadState.Empty)
}