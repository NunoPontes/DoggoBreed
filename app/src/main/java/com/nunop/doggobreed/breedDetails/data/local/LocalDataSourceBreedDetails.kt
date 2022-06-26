package com.nunop.doggobreed.breedDetails.data.local

import com.nunop.doggobreed.breedDetails.data.local.entities.BreedDetailsDB

interface LocalDataSourceBreedDetails {

    suspend fun insertBreedDetails(breedDetails: BreedDetailsDB)
    suspend fun getBreedDetails(breed: String): BreedDetailsDB?
}