package com.exmaple.locationphotostesttask.authentication.domain

import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.core.ManagerResource

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface ValidationChain {

 suspend fun validate(
    login: String,
    password: String,
    repeatedPassword: String,
    success: suspend ()->AuthenticationState,
    failure: suspend (Int)->AuthenticationState
  ): AuthenticationState


  class CheckLogin(
    private val nextChainItem: ValidationChain,
  ): ValidationChain{

    override suspend fun validate(
      login: String,
      password: String,
      repeatedPassword: String,
      success: suspend ()->AuthenticationState,
      failure: suspend (Int)->AuthenticationState
    ): AuthenticationState {
      return if(login.isEmpty()) failure.invoke(R.string.empty_login_message)
        else nextChainItem.validate(login, password, repeatedPassword,success, failure)
    }
  }

  class CheckPassword(
    private val nextChainItem: ValidationChain,
  ): ValidationChain{

    override suspend fun validate(
      login: String,
      password: String,
      repeatedPassword: String,
      success: suspend ()->AuthenticationState,
      failure: suspend (Int)->AuthenticationState
    ): AuthenticationState {
      return if(password.isEmpty()) failure.invoke(R.string.empty_password_message)
      else  nextChainItem.validate(login, password, repeatedPassword, success, failure)
    }

  }

  class CheckRepeatedPassword(
    private val nextChainItem: ValidationChain,
  ): ValidationChain{

    override suspend fun validate(
      login: String,
      password: String,
      repeatedPassword: String,
      success: suspend ()->AuthenticationState,
      failure: suspend (Int)->AuthenticationState
    ): AuthenticationState {

     return if(repeatedPassword.isEmpty())
       failure.invoke(R.string.empty_repeated_password_message)
      else if(repeatedPassword!=password)
       failure.invoke(R.string.wrong_repeated_password_message)
      else
        nextChainItem.validate(login, password, repeatedPassword,success, failure)
    }

  }

  object LastChainItem : ValidationChain{
    override suspend fun validate(
      login: String,
      password: String,
      repeatedPassword: String,
      success: suspend ()->AuthenticationState,
      failure: suspend (Int)->AuthenticationState
    ): AuthenticationState = success.invoke()

  }
}