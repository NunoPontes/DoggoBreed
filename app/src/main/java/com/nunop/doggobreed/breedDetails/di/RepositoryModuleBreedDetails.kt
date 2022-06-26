package com.nunop.doggobreed.breedDetails.di

import com.nunop.doggobreed.breedDetails.data.local.LocalDataSourceBreedDetails
import com.nunop.doggobreed.breedDetails.data.local.LocalDataSourceBreedDetailsImpl
import com.nunop.doggobreed.breedDetails.data.remote.RemoteDataSourceBreedDetails
import com.nunop.doggobreed.breedDetails.data.remote.RemoteDataSourceBreedDetailsImpl
import com.nunop.doggobreed.breedDetails.data.repository.BreedDetailsRepositoryImpl
import com.nunop.doggobreed.breedDetails.domain.repository.BreedDetailsRepository
import com.nunop.doggobreed.core.data.local.DoggoDatabase
import com.nunop.doggobreed.core.data.remote.DogBreedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModuleBreedDetails {

    @Provides
    @ViewModelScoped
    fun provideBreedDetailsRepository(
        remoteDataSourceBreedDetails: RemoteDataSourceBreedDetails,
        localDataSourceBreedDetails: LocalDataSourceBreedDetails
    ) =
        BreedDetailsRepositoryImpl(
            remoteDataSourceBreedDetails,
            localDataSourceBreedDetails
        ) as BreedDetailsRepository

    @Provides
    @ViewModelScoped
    fun provideRemoteDataSource(api: DogBreedApi) = RemoteDataSourceBreedDetailsImpl(
        api
    ) as RemoteDataSourceBreedDetails

    @Provides
    @ViewModelScoped
    fun provideLocalDataSource(db: DoggoDatabase) =
        LocalDataSourceBreedDetailsImpl(db) as LocalDataSourceBreedDetails
}