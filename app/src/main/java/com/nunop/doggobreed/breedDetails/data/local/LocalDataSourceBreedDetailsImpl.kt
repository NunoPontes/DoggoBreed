package com.nunop.doggobreed.breedDetails.data.local

import com.nunop.doggobreed.breedDetails.data.local.entities.BreedDetailsDB
import com.nunop.doggobreed.core.data.local.DoggoDatabase
import javax.inject.Inject

class LocalDataSourceBreedDetailsImpl @Inject constructor(private val db: DoggoDatabase) :
    LocalDataSourceBreedDetails {

    override suspend fun insertBreedDetails(breedDetails: BreedDetailsDB) {
        db.breedDetailsDao.insertBreedDetails(breedDetails)
    }

    override suspend fun getBreedDetails(breed: String): BreedDetailsDB? {
        return db.breedDetailsDao.getBreedDetails(breed)
    }
}