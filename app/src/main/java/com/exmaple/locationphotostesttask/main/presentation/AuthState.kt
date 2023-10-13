package com.exmaple.locationphotostesttask.main.presentation

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.authentication.presentation.AuthenticationActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
sealed interface AuthState{

 fun apply(
  navController: NavController,
  viewModel: MainViewModel,
  activity: MainActivity
 )

 object NavigateToAuthScreen: AuthState {
  override fun apply(
   navController: NavController,
   viewModel: MainViewModel,
   activity: MainActivity
  ) {
   viewModel.resetAuthState()
   activity.startActivity(Intent(activity,AuthenticationActivity::class.java).apply {
    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
   })
  }
 }


 object Empty: AuthState {
  override fun apply(
   navController: NavController,
   viewModel: MainViewModel,
   activity: MainActivity
  ) = Unit
 }

}