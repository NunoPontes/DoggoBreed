package com.nunop.doggobreed.breeds.domain.use_case

import com.nunop.doggobreed.breeds.data.remote.dto.AllDogBreeds
import com.nunop.doggobreed.breeds.domain.repository.DogBreedRepository
import com.nunop.doggobreed.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDogBreeds @Inject constructor(private val repository: DogBreedRepository) {

    suspend operator fun invoke(): Flow<Resource<AllDogBreeds>> {
        return repository.getDogBreeds()
    }
}