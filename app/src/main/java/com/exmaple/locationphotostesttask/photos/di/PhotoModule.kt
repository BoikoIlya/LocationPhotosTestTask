package com.exmaple.locationphotostesttask.photos.di

import android.content.Context
import com.exmaple.locationphotostesttask.authentication.data.cloud.AuthenticationService
import com.exmaple.locationphotostesttask.authentication.presentation.register.RegisterStateCommunication
import com.exmaple.locationphotostesttask.core.ConnectionChecker
import com.exmaple.locationphotostesttask.core.DateTimeParser
import com.exmaple.locationphotostesttask.core.LocationPhotosDB
import com.exmaple.locationphotostesttask.core.PagingSource
import com.exmaple.locationphotostesttask.main.di.AppModuleProvides
import com.exmaple.locationphotostesttask.photos.data.PhotosRepository
import com.exmaple.locationphotostesttask.photos.data.cache.PhotoCache
import com.exmaple.locationphotostesttask.photos.data.cache.PhotosDao
import com.exmaple.locationphotostesttask.photos.data.cloud.CloudPhotosDataSource
import com.exmaple.locationphotostesttask.photos.data.cloud.PhotosService
import com.exmaple.locationphotostesttask.photos.domain.BitmapRotator
import com.exmaple.locationphotostesttask.photos.domain.LocationProvider
import com.exmaple.locationphotostesttask.photos.domain.PhotoDomainState
import com.exmaple.locationphotostesttask.photos.domain.PhotosInteractor
import com.exmaple.locationphotostesttask.photos.domain.UriToBase64Parser
import com.exmaple.locationphotostesttask.photos.presentation.PhotosListCommunication
import com.exmaple.locationphotostesttask.photos.presentation.PhotosLoadStateCommunication
import com.exmaple.locationphotostesttask.photos.presentation.PhotosToUiMapper
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/

@Module
@InstallIn(ViewModelComponent::class)
interface PhotoModule {

 @Binds
 @ViewModelScoped
 fun bindPhotosToUiMapper(obj: PhotosToUiMapper): PhotoDomainState.Mapper

 @Binds
 @Reusable
 fun bindPhotosLoadStateCommunication(obj: PhotosLoadStateCommunication.Base): PhotosLoadStateCommunication

 @Binds
 @Reusable
 fun bindPhotosListCommunication(obj: PhotosListCommunication.Base): PhotosListCommunication

 @Binds
 @ViewModelScoped
 fun bindPhotosInteractor(obj: PhotosInteractor.Base): PhotosInteractor

 @Binds
 @ViewModelScoped
 fun bindPhotosRepository(obj: PhotosRepository.Base): PhotosRepository

 @Binds
 @ViewModelScoped
 fun bindPhotosPagingSource(obj: PagingSource.PhotosPagingSource): PagingSource<PhotoCache>

 @Binds
 @ViewModelScoped
 fun bindDateTimeParser(obj: DateTimeParser.Base): DateTimeParser

 @Binds
 @ViewModelScoped
 fun bindUriToBase64Parser(obj: UriToBase64Parser.Base): UriToBase64Parser

 @Binds
 @ViewModelScoped
 fun bindCloudPhotosDataSource(obj: CloudPhotosDataSource.Base): CloudPhotosDataSource

 @Binds
 @ViewModelScoped
 fun bindBitmapRotator(obj: BitmapRotator.Base): BitmapRotator

 @Binds
 @ViewModelScoped
 fun bindConnectionChecker(obj: ConnectionChecker.Base): ConnectionChecker
}

@Module
@InstallIn(ViewModelComponent::class)
class PhotoModuleProvides {

 @Provides
 @ViewModelScoped
 fun providePhotosDao(db: LocationPhotosDB): PhotosDao = db.getPhotosDao()

 @Provides
 @ViewModelScoped
 fun providePhotosService(
  client: OkHttpClient,
  builder: Retrofit.Builder,
  converterFactory: GsonConverterFactory,
 ): PhotosService {
  return builder
   .baseUrl(AppModuleProvides.baseUrl)
   .addConverterFactory(converterFactory)
   .client(client)
   .build()
   .create(PhotosService::class.java)
 }


 @Provides
 @ViewModelScoped
 fun provideLocationProvider(@ApplicationContext context: Context): LocationProvider{
   val client = LocationServices.getFusedLocationProviderClient(context)
   return LocationProvider.Base(client)
 }
}