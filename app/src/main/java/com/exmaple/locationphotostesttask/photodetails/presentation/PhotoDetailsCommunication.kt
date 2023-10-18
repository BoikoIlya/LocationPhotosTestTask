package com.exmaple.locationphotostesttask.photodetails.presentation

import com.exmaple.locationphotostesttask.core.Communication
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 16.10.2023.
 **/
interface PhotoDetailsCommunication: Communication.Mutable<PhotoDetailsUi> {

    class Base @Inject constructor(): PhotoDetailsCommunication,
            Communication.UiUpdate<PhotoDetailsUi>(PhotoDetailsUi.Empty)
}