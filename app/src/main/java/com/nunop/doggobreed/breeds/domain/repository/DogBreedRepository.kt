package com.nunop.doggobreed.breeds.domain.repository

import com.nunop.doggobreed.breeds.data.remote.dto.AllDogBreeds
import com.nunop.doggobreed.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DogBreedRepository {

    suspend fun getDogBreeds(): Flow<Resource<AllDogBreeds>>
}