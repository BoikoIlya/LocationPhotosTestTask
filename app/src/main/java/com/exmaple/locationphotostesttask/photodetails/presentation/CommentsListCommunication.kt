package com.exmaple.locationphotostesttask.photodetails.presentation

import com.exmaple.locationphotostesttask.core.Communication
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
interface CommentsListCommunication: Communication.Mutable<List<CommentUi>> {

 class Base @Inject constructor(): CommentsListCommunication,
  Communication.UiUpdate<List<CommentUi>>(emptyList())
}