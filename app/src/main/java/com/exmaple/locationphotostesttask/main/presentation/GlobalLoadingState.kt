package com.exmaple.locationphotostesttask.main.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
sealed interface GlobalLoadingState{

 fun apply(context: Context)

 companion object{
  private const val global_loading_tag = "global_loading_tag"
 }

 object Loading: GlobalLoadingState {
  override fun apply(context: Context) {
   val loadingDialog = GlobalLoadingDialogFragment()
   loadingDialog.show((context as AppCompatActivity).supportFragmentManager, global_loading_tag)
  }
 }

 object Dismiss: GlobalLoadingState {
  override fun apply(context: Context) {
   val fragmentManager = (context as AppCompatActivity).supportFragmentManager
   val dialog = fragmentManager.findFragmentByTag(global_loading_tag) as DialogFragment?
   dialog?.dismiss()
  }
 }

 object Empty: GlobalLoadingState{
  override fun apply(context: Context) = Unit
 }

}