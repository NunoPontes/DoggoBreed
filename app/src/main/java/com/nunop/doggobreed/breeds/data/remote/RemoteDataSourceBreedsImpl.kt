package com.nunop.doggobreed.breeds.data.remote

import com.nunop.doggobreed.core.data.remote.DogBreedApi
import javax.inject.Inject

class RemoteDataSourceBreedsImpl @Inject constructor(private val api: DogBreedApi) : RemoteDataSourceBreeds {

    override suspend fun getDogBreeds() = api.getDogBreeds()

}