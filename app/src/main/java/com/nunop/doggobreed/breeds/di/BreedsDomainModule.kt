package com.nunop.doggobreed.breeds.di

import com.nunop.doggobreed.breeds.domain.repository.DogBreedRepository
import com.nunop.doggobreed.breeds.domain.use_case.BreedsUseCases
import com.nunop.doggobreed.breeds.domain.use_case.GetDogBreeds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object BreedsDomainModule {

    @ViewModelScoped
    @Provides
    fun provideBreedsUseCases(
        repository: DogBreedRepository
    ): BreedsUseCases {
        return BreedsUseCases(
            getDogBreeds = GetDogBreeds(repository)
        )
    }
}