package com.nunop.doggobreed.breeds.data.local

import com.nunop.doggobreed.breeds.data.local.entities.AllDogBreedsDB
import com.nunop.doggobreed.core.data.local.DoggoDatabase
import javax.inject.Inject

class LocalDataSourceBreedsImpl @Inject constructor(private val db: DoggoDatabase):
    LocalDataSourceBreeds {
    override suspend fun insertBreeds(dogBreeds: AllDogBreedsDB) {
        db.allDogBreedsDao.insertBreeds(dogBreeds)
    }

    override suspend fun getAllBreeds(): AllDogBreedsDB? {
        return db.allDogBreedsDao.getAllDogBreeds()
    }
}