package com.exmaple.locationphotostesttask.photodetails.presentation

import com.exmaple.locationphotostesttask.core.Communication
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
interface CommentSectionStateCommunication: Communication.Mutable<CommentSectionState> {

 class Base @Inject constructor(): CommentSectionStateCommunication,
         Communication.UiUpdate<CommentSectionState>(CommentSectionState.DisableLoading.NotReadyToSend())
}