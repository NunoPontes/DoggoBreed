package com.nunop.doggobreed.breedDetails.data.remote

import com.nunop.doggobreed.breedDetails.data.remote.dto.BreedDetails
import retrofit2.Response

interface RemoteDataSourceBreedDetails {

    suspend fun getBreedDetails(breed: String, numberPhotos: Int): Response<BreedDetails>
    suspend fun getSubBreedDetails(breed: String, subBreed: String, numberPhotos: Int):
            Response<BreedDetails>
}