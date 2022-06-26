package com.nunop.doggobreed.breedDetails.di

import com.nunop.doggobreed.breedDetails.domain.repository.BreedDetailsRepository
import com.nunop.doggobreed.breedDetails.domain.use_case.BreedDetailsUseCases
import com.nunop.doggobreed.breedDetails.domain.use_case.GetBreedDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object BreedDetailsDomainModule {

    @ViewModelScoped
    @Provides
    fun provideBreedDetailsUseCases(
        repository: BreedDetailsRepository
    ): BreedDetailsUseCases {
        return BreedDetailsUseCases(
            getBreedDetailsUseCases = GetBreedDetails(repository)
        )
    }
}