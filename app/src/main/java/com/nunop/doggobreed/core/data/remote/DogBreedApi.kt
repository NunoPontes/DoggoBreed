package com.nunop.doggobreed.core.data.remote

import com.nunop.doggobreed.breedDetails.data.remote.dto.BreedDetails
import com.nunop.doggobreed.breeds.data.remote.dto.AllDogBreeds
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogBreedApi {

    @GET("breeds/list/all")
    suspend fun getDogBreeds(): Response<AllDogBreeds>

    @GET("breed/{breed}/images/random/{numberPhotos}")
    suspend fun getDogBreedsPhotos(
        @Path("breed") breed: String,
        @Path("numberPhotos") numberPhotos: Int
    ): Response<BreedDetails>

    @GET("breed/{breed}/{subBreed}/images/random/{numberPhotos}")
    suspend fun getDogSubBreedsPhotos(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String,
        @Path("numberPhotos") numberPhotos: Int
    ): Response<BreedDetails>
}