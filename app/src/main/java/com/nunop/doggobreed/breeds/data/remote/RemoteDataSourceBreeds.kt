package com.nunop.doggobreed.breeds.data.remote

import com.nunop.doggobreed.breeds.data.remote.dto.AllDogBreeds
import retrofit2.Response

interface RemoteDataSourceBreeds {

    suspend fun getDogBreeds(): Response<AllDogBreeds>
}