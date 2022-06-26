package com.nunop.doggobreed.breedDetails.data.repository

import com.nunop.doggobreed.breedDetails.data.remote.dto.BreedDetails
import com.nunop.doggobreed.breedDetails.domain.repository.BreedDetailsRepository
import com.nunop.doggobreed.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeBreedDetailsRepository : BreedDetailsRepository {

    override suspend fun getBreedDetails(breed: String): Flow<Resource<BreedDetails>> {
        val breedDetails = BreedDetails(
            status = "Success",
            message = listOf("Breed1", "Breed2")
        )
        return flow {
            emit(Resource.Success(breedDetails))
        }
    }

    override suspend fun getSubBreedDetails(
        breed: String,
        subBreed: String
    ): Flow<Resource<BreedDetails>> {

        val subBreedDetails = BreedDetails(
            status = "Success",
            message = listOf("SubBreed1", "SubBreed2", "SubBreed3")
        )
        return flow {
            emit(Resource.Success(subBreedDetails))
        }
    }
}