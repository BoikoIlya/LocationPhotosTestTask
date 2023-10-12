package com.exmaple.locationphotostesttask.authentication.domain

import com.exmaple.locationphotostesttask.authentication.data.AuthenticationRepository
import com.exmaple.locationphotostesttask.core.HandleResponse
import com.exmaple.locationphotostesttask.core.ManagerResource
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface LoginInteractor{

 suspend fun login(
  login:String,
  password:String,
 ): AuthenticationState

 class Base @Inject constructor(
  private val managerResource: ManagerResource,
  private val handleResponse: HandleResponse,
  private val repository: AuthenticationRepository
 ): LoginInteractor {

  override suspend fun login(login: String, password: String): AuthenticationState
  = handleResponse.handle({
   val chain: ValidationChain =
    ValidationChain.CheckLogin(
     ValidationChain.CheckPassword(
      ValidationChain.LastChainItem))

    chain.validate(login, password,"",
     success ={
      repository.login(login, password)
      AuthenticationState.Success
    },
     failure ={ messageId->
      AuthenticationState.Failure(managerResource.getString(messageId))
    })
  },{ message,_->
   return@handle AuthenticationState.Failure(message)
  })

  }
 }
