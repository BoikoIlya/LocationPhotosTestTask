package com.exmaple.locationphotostesttask.authentication.presentation

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import androidx.navigation.NavController
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.main.presentation.MainActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
sealed interface AuthenticationUiState {

 fun apply(
  context: Context,
  button: MaterialButton,
  progressBar: ProgressBar,
  vararg editTexts: TextInputEditText,
 )

 object Loading: AuthenticationUiState {
  override fun apply(
   context: Context,
   button: MaterialButton,
   progressBar: ProgressBar,
   vararg editTexts: TextInputEditText,
  ) {
   editTexts.forEach { it.isEnabled = false }
   button.visibility = View.GONE
   progressBar.visibility = View.VISIBLE
  }
 }

 object Success: AuthenticationUiState {
  override fun apply(
   context: Context,
   button: MaterialButton,
   progressBar: ProgressBar,
   vararg editTexts: TextInputEditText,
  ) {
   progressBar.visibility = View.GONE
   context.startActivity(Intent(context,MainActivity::class.java).apply {
    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
   })
  }
 }

  object Failure : AuthenticationUiState {
   override fun apply(
    context: Context,
    button: MaterialButton,
    progressBar: ProgressBar,
    vararg editTexts: TextInputEditText,
   ) {
    editTexts.forEach { it.isEnabled = true }
    button.visibility = View.VISIBLE
    progressBar.visibility = View.GONE
   }
  }

 object Empty: AuthenticationUiState {
  override fun apply(
   context: Context,
   button: MaterialButton,
   progressBar: ProgressBar,
   vararg editTexts: TextInputEditText,
  ) = Unit
 }
}