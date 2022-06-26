package com.nunop.doggobreed.breedDetails.domain.repository

import com.nunop.doggobreed.breedDetails.data.remote.dto.BreedDetails
import com.nunop.doggobreed.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BreedDetailsRepository {

    suspend fun getBreedDetails(
        breed: String
    ): Flow<Resource<BreedDetails>>

    suspend fun getSubBreedDetails(
        breed: String,
        subBreed: String
    ): Flow<Resource<BreedDetails>>
}