package com.exmaple.locationphotostesttask.main.presentation

import androidx.navigation.NavController
import com.exmaple.locationphotostesttask.R

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
sealed interface AuthState{

 fun apply(
  navController: NavController,
  viewModel: MainViewModel
 )

 object NavigateToAuthScreen: AuthState {
  override fun apply(navController: NavController,viewModel: MainViewModel) {
    navController.navigate(R.id.action_imagesFragment_to_authenticationFragment)
    viewModel.resetAuthState()
  }
 }

 object Empty: AuthState {
  override fun apply(navController: NavController, viewModel: MainViewModel) = Unit
 }

}