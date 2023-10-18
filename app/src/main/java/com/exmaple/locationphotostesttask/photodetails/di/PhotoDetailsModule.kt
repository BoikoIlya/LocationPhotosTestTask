package com.exmaple.locationphotostesttask.photodetails.di

import android.content.Context
import com.exmaple.locationphotostesttask.core.LocationPhotosDB
import com.exmaple.locationphotostesttask.core.PagingSource
import com.exmaple.locationphotostesttask.main.di.AppModuleProvides
import com.exmaple.locationphotostesttask.photodetails.data.PhotoDetailsRepository
import com.exmaple.locationphotostesttask.photodetails.data.cache.CommentCache
import com.exmaple.locationphotostesttask.photodetails.data.cache.CommentsDao
import com.exmaple.locationphotostesttask.photodetails.data.cloud.CommentsCloudDataSource
import com.exmaple.locationphotostesttask.photodetails.data.cloud.CommentsService
import com.exmaple.locationphotostesttask.photodetails.domain.PhotoDetailsDomainState
import com.exmaple.locationphotostesttask.photodetails.domain.PhotoDetailsInteractor
import com.exmaple.locationphotostesttask.photodetails.presentation.CommentSectionStateCommunication
import com.exmaple.locationphotostesttask.photodetails.presentation.CommentsListCommunication
import com.exmaple.locationphotostesttask.photodetails.presentation.CommentsLoadStateCommunication
import com.exmaple.locationphotostesttask.photodetails.presentation.CommentsToUiMapper
import com.exmaple.locationphotostesttask.photodetails.presentation.PhotoDetailsCommunication
import com.exmaple.locationphotostesttask.photodetails.presentation.PhotoDetailsUi
import com.exmaple.locationphotostesttask.photos.data.cache.PhotosDao
import com.exmaple.locationphotostesttask.photos.data.cloud.PhotosService
import com.exmaple.locationphotostesttask.photos.domain.LocationProvider
import com.exmaple.locationphotostesttask.photos.domain.PhotoDomain
import com.exmaple.locationphotostesttask.photos.presentation.PhotoUi
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
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
@Module
@InstallIn(ViewModelComponent::class)
interface PhotoDetailsModule {

 @Binds
 @Reusable
 fun bindPhotoDetailsCommunication(obj: PhotoDetailsCommunication.Base): PhotoDetailsCommunication

 @Binds
 @Reusable
 fun bindCommentsLoadStateCommunication(obj: CommentsLoadStateCommunication.Base): CommentsLoadStateCommunication

 @Binds
 @Reusable
 fun bindCommentsListCommunication(obj: CommentsListCommunication.Base): CommentsListCommunication

 @Binds
 @Reusable
 fun bindCommentSectionStateCommunication(obj: CommentSectionStateCommunication.Base): CommentSectionStateCommunication

 @Binds
 @ViewModelScoped
 fun bindCommentsToUiMapper(obj: CommentsToUiMapper): PhotoDetailsDomainState.Mapper

 @Binds
 @ViewModelScoped
 fun bindPhotoDetailsInteractor(obj: PhotoDetailsInteractor.Base): PhotoDetailsInteractor

 @Binds
 @ViewModelScoped
 fun bindPhotoDetailsRepository(obj: PhotoDetailsRepository.Base): PhotoDetailsRepository

 @Binds
 @ViewModelScoped
 fun bindCommentsCloudDataSource(obj: CommentsCloudDataSource.Base): CommentsCloudDataSource

 @Binds
 @ViewModelScoped
 fun bindToPhotoDetailsUiMapper(obj: PhotoDomain.ToPhotoDetailsUiMapper): PhotoDomain.Mapper<PhotoDetailsUi>

 @Binds
 @ViewModelScoped
 fun bindToCommentsPagingSource(obj: PagingSource.CommentsPagingSource): PagingSource<CommentCache>

}


@Module
@InstallIn(ViewModelComponent::class)
class PhotoDetailsModuleProvides {

 @Provides
 @ViewModelScoped
 fun provideCommentsDao(db: LocationPhotosDB): CommentsDao = db.getCommentsDao()

 @Provides
 @ViewModelScoped
 fun provideCommentsServiceService(
  client: OkHttpClient,
  builder: Retrofit.Builder,
  converterFactory: GsonConverterFactory,
 ): CommentsService {
  return builder
   .baseUrl(AppModuleProvides.baseUrl)
   .addConverterFactory(converterFactory)
   .client(client)
   .build()
   .create(CommentsService::class.java)
 }


 }