package com.nunop.doggobreed.breeds.data.mapper

import com.nunop.doggobreed.breeds.data.remote.dto.AllDogBreeds
import com.nunop.doggobreed.breeds.data.local.entities.AllDogBreedsDB

fun AllDogBreeds.toAllDogBreedsDB(): AllDogBreedsDB? {
    return AllDogBreedsDB(
        message = message ?: return null,
        status = status ?: return null
    )
}

