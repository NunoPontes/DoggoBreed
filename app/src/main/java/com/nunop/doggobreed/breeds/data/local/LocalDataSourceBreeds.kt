package com.nunop.doggobreed.breeds.data.local

import com.nunop.doggobreed.breeds.data.local.entities.AllDogBreedsDB

interface LocalDataSourceBreeds {

    suspend fun insertBreeds(dogBreeds: AllDogBreedsDB)
    suspend fun getAllBreeds(): AllDogBreedsDB?
}