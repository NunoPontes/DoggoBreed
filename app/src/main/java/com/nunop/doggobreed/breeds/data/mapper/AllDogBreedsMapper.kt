package com.nunop.doggobreed.breeds.data.mapper

import com.nunop.doggobreed.breeds.data.remote.dto.AllDogBreeds
import com.nunop.doggobreed.breeds.data.local.entities.AllDogBreedsDB

fun AllDogBreedsDB.toAllDogBreeds(): AllDogBreeds {
    return AllDogBreeds(
        message = message,
        status = status
    )
}

