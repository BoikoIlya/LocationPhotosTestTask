package com.exmaple.locationphotostesttask.authentication.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.exmaple.locationphotostesttask.authentication.data.AuthenticationRepository
import com.exmaple.locationphotostesttask.authentication.data.cache.TokenStore
import com.exmaple.locationphotostesttask.authentication.data.cloud.AuthenticationService
import com.exmaple.locationphotostesttask.authentication.domain.AuthenticationState
import com.exmaple.locationphotostesttask.authentication.domain.LoginInteractor
import com.exmaple.locationphotostesttask.authentication.domain.RegisterInteractor
import com.exmaple.locationphotostesttask.authentication.presentation.AuthenticationMapper
import com.exmaple.locationphotostesttask.authentication.presentation.login.LoginMapper
import com.exmaple.locationphotostesttask.authentication.presentation.login.LoginStateCommunication
import com.exmaple.locationphotostesttask.authentication.presentation.register.RegisterMapper
import com.exmaple.locationphotostesttask.authentication.presentation.register.RegisterStateCommunication
import com.exmaple.locationphotostesttask.core.HandleResponseData
import com.exmaple.locationphotostesttask.main.di.AppModuleProvides.Companion.baseUrl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
@Module
@InstallIn(ViewModelComponent::class)
interface AuthenticationModule {

 @Binds
 @Reusable
 fun bindRegisterStateCommunication(obj: RegisterStateCommunication.Base): RegisterStateCommunication

 @Binds
 @Reusable
 fun bindLoginStateCommunication(obj: LoginStateCommunication.Base): LoginStateCommunication

 @Binds
 @ViewModelScoped
 fun bindLoginMapper(obj: LoginMapper.Base): LoginMapper

 @Binds
 @ViewModelScoped
 fun bindRegisterMapper(obj: RegisterMapper.Base): RegisterMapper

 @Binds
 @ViewModelScoped
 fun bindRegisterInteractor(obj: RegisterInteractor.Base): RegisterInteractor

 @Binds
 @ViewModelScoped
 fun bindLoginInteractor(obj: LoginInteractor.Base): LoginInteractor

 @Binds
 @ViewModelScoped
 fun bindAuthenticationRepository(obj: AuthenticationRepository.Base): AuthenticationRepository

 @Binds
 @ViewModelScoped
 fun bindHandleResponseData(obj: HandleResponseData.Base): HandleResponseData


}

@Module
@InstallIn(ViewModelComponent::class)
class AuthenticationModuleProvides{


 @Provides
 @ViewModelScoped
 fun provideFavoritesService(
  client: OkHttpClient,
  builder: Retrofit.Builder,
  converterFactory: GsonConverterFactory,
 ): AuthenticationService {
  return builder
   .baseUrl(baseUrl)
   .addConverterFactory(converterFactory)
   .client(client)
   .build()
   .create(AuthenticationService::class.java)
 }

}