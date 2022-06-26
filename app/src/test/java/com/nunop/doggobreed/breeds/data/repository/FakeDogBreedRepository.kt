package com.nunop.doggobreed.breeds.data.repository

import com.nunop.doggobreed.breeds.data.remote.dto.AllDogBreeds
import com.nunop.doggobreed.breeds.domain.repository.DogBreedRepository
import com.nunop.doggobreed.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDogBreedRepository : DogBreedRepository {

    override suspend fun getDogBreeds(): Flow<Resource<AllDogBreeds>> {
        val map: MutableMap<String, List<String>> = mutableMapOf()
        map["Breed"] = listOf("SubBreed1", "SubBreed2")

        val dogBreeds = AllDogBreeds(
            status = "Success",
            message = map
        )
        return flow {
            emit(Resource.Success(dogBreeds))
        }
    }
}