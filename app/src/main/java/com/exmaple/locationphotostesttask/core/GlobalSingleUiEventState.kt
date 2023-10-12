package com.exmaple.locationphotostesttask.core

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
sealed interface GloabalSingleUiEventState{


 fun apply(
  fragmentManager: FragmentManager,
  context: Context,
  binding: ActivityMainBinding,
 )


 abstract class ShowSnackBar(
  private val message: String,
  private val bgColorId: Int,
 ): GloabalSingleUiEventState {

  override fun apply(
   fragmentManager: FragmentManager,
   context: Context,
   binding: ActivityMainBinding,
  ) = with(binding) {

   val imm =
    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
   imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

   val snackBar = Snackbar.make(fragmentContainer, message, 5000)
   val snackBarView: View = snackBar.view
   snackBarView.background =
    AppCompatResources.getDrawable(context, R.drawable.snack_bar_rounded_corners)
   snackBar
    .setBackgroundTint(context.getColor(bgColorId))
    .setTextColor(context.getColor(R.color.white))
    .show()
  }


  data class Error(
   private val message: String,
  ) : ShowSnackBar(message, R.color.snack_bar_red)

  data class Success(
   private val message: String,
  ) : ShowSnackBar(message, R.color.snack_bar_green)
 }




 data class ShowDialog(
  private val dialog: DialogFragment,
 ) : GloabalSingleUiEventState{

  override fun apply(
   fragmentManager: FragmentManager,
   context: Context,
   binding: ActivityMainBinding,
  ) = dialog.show(fragmentManager, dialog.tag)

 }


}