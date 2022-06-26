package com.nunop.doggobreed.breedDetails.domain.use_case

import com.nunop.doggobreed.breedDetails.data.remote.dto.BreedDetails
import com.nunop.doggobreed.breedDetails.domain.repository.BreedDetailsRepository
import com.nunop.doggobreed.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBreedDetails @Inject constructor(private val repository: BreedDetailsRepository) {

    suspend operator fun invoke(
        breed: String,
        subBreed: String? = null
    ): Flow<Resource<BreedDetails>> {
        return if (subBreed != null) {
            repository.getSubBreedDetails(breed = breed, subBreed = subBreed)
        } else {
            repository.getBreedDetails(breed)
        }
    }
}