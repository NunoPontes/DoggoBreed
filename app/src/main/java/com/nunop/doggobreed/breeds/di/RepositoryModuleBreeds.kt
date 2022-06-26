package com.nunop.doggobreed.breeds.di

import com.nunop.doggobreed.breeds.data.remote.RemoteDataSourceBreeds
import com.nunop.doggobreed.breeds.data.remote.RemoteDataSourceBreedsImpl
import com.nunop.doggobreed.breeds.data.repository.DogBreedRepositoryImpl
import com.nunop.doggobreed.breeds.domain.repository.DogBreedRepository
import com.nunop.doggobreed.core.data.local.DoggoDatabase
import com.nunop.doggobreed.breeds.data.local.LocalDataSourceBreeds
import com.nunop.doggobreed.breeds.data.local.LocalDataSourceBreedsImpl
import com.nunop.doggobreed.core.data.remote.DogBreedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModuleBreeds {

    @Provides
    @ViewModelScoped
    fun provideDogBreedRepository(
        remoteDataSourceBreeds: RemoteDataSourceBreeds,
        localDataSourceBreeds: LocalDataSourceBreeds
    ) =
        DogBreedRepositoryImpl(remoteDataSourceBreeds, localDataSourceBreeds) as DogBreedRepository

    @Provides
    @ViewModelScoped
    fun provideRemoteDataSource(api: DogBreedApi) = RemoteDataSourceBreedsImpl(
        api
    ) as RemoteDataSourceBreeds

    @Provides
    @ViewModelScoped
    fun provideLocalDataSource(db: DoggoDatabase) = LocalDataSourceBreedsImpl(db) as LocalDataSourceBreeds
}