package com.exmaple.locationphotostesttask.photodetails.presentation

import android.util.Log
import com.exmaple.locationphotostesttask.databinding.CommentItemBinding

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
data class CommentUi(
 private val id: Int,
 private val text: String,
 private val dataAndTime: String,
 private val photoId: Int,
){

 fun showToUi(
  binding: CommentItemBinding,
  onLongClick:(Int)->Unit
 ) = with(binding) {
   commentTextView.text = text
   commentDataTimeTextView.text = dataAndTime
   root.setOnLongClickListener {
    onLongClick.invoke(id)
    true
   }
 }

 fun map(commentUi: CommentUi) = commentUi.id==this.id

}
