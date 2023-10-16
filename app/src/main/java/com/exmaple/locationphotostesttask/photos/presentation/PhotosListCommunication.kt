package com.exmaple.locationphotostesttask.photos.presentation

import com.exmaple.locationphotostesttask.core.Communication
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
interface PhotosListCommunication: Communication.Mutable<List<PhotoUi>> {

 class Base @Inject constructor(): PhotosListCommunication, Communication.UiUpdate<List<PhotoUi>>(emptyList())
}