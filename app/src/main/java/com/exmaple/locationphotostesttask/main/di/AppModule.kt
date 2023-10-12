package com.exmaple.locationphotostesttask.main.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.exmaple.locationphotostesttask.authentication.data.cache.TokenStore
import com.exmaple.locationphotostesttask.authentication.di.AuthenticationModuleProvides
import com.exmaple.locationphotostesttask.core.DispatchersList
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventStateCommunication
import com.exmaple.locationphotostesttask.core.HandleError
import com.exmaple.locationphotostesttask.core.HandleResponse
import com.exmaple.locationphotostesttask.core.HandleResponseData
import com.exmaple.locationphotostesttask.core.ManagerResource
import com.exmaple.locationphotostesttask.main.data.AuthorizationRepository
import com.exmaple.locationphotostesttask.main.presentation.AuthStateCommunication
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
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
@InstallIn(SingletonComponent::class)
class AppModuleProvides {

 companion object{
  private const val data_store_name: String = "data_store"
  const val baseUrl = "https://junior.balinasoft.com"
  private const val token_key: String ="token_key"
 }

 @Singleton
 @Provides
 fun provideSettingsDataStore(
  @ApplicationContext
  context: Context,
  dispatchersList: DispatchersList,
 ): DataStore<Preferences> {
  return PreferenceDataStoreFactory.create(
   corruptionHandler = ReplaceFileCorruptionHandler(
    produceNewData = { emptyPreferences() }
   ),
   migrations = listOf(SharedPreferencesMigration(context,data_store_name)),
   scope = CoroutineScope(dispatchersList.io() + SupervisorJob()),
   produceFile ={ context.preferencesDataStoreFile(data_store_name) }
  )
 }



 @Provides
 @Singleton
 fun provideConverterFactory(): GsonConverterFactory{
  return GsonConverterFactory.create()
 }

 @Provides
 @Singleton
 fun provideRetrofitBuilder(): Retrofit.Builder {
  return Retrofit.Builder()
 }

 @Provides
 @Singleton
 fun provideInterceptor(): Interceptor {
  return HttpLoggingInterceptor()
   .setLevel(HttpLoggingInterceptor.Level.BODY)
 }

 @Provides
 @Singleton
 fun provideHttpClient(
  interceptor: Interceptor,
 ): OkHttpClient {
  return OkHttpClient.Builder()
   .addInterceptor(interceptor)
   .build()
 }

 @Singleton
 @Provides
 fun provideTokenStore(dataStore: DataStore<Preferences>): TokenStore {
  return TokenStore.Base(stringPreferencesKey(token_key), dataStore)
 }

}

@Module
@InstallIn(SingletonComponent::class)
interface AppModule{

 @Binds
 @Singleton
 fun bindAuthorizationRepository(obj: AuthorizationRepository.Base): AuthorizationRepository

 @Binds
 @Singleton
 fun bindAuthStateCommunication(obj: AuthStateCommunication.Base): AuthStateCommunication

 @Binds
 @Singleton
 fun bindManagerResource(obj: ManagerResource.Base): ManagerResource

 @Binds
 @Singleton
 fun bindHandleError(obj: HandleError.Base): HandleError

 @Binds
 @Singleton
 fun bindHandleResponse(obj: HandleResponse.Base): HandleResponse

 @Binds
 @Singleton
 fun bindDispatchersList(obj: DispatchersList.Base): DispatchersList

 @Binds
 @Singleton
 fun bindGlobalSingleUiEventStateCommunication(obj: GlobalSingleUiEventStateCommunication.Base): GlobalSingleUiEventStateCommunication
}