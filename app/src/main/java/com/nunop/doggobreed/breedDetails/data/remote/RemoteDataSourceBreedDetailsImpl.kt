package com.nunop.doggobreed.breedDetails.data.remote

import com.nunop.doggobreed.core.data.remote.DogBreedApi
import javax.inject.Inject

class RemoteDataSourceBreedDetailsImpl @Inject constructor(private val api: DogBreedApi) :
    RemoteDataSourceBreedDetails {

    override suspend fun getBreedDetails(breed: String, numberPhotos: Int) =
        api.getDogBreedsPhotos(
            breed = breed,
            numberPhotos = numberPhotos
        )

    override suspend fun getSubBreedDetails(breed: String, subBreed: String, numberPhotos: Int) =
        api.getDogSubBreedsPhotos(
            breed = breed,
            subBreed = subBreed,
            numberPhotos = numberPhotos
        )

}