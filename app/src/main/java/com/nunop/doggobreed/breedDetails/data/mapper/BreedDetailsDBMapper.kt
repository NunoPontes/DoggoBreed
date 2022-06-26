package com.nunop.doggobreed.breedDetails.data.mapper

import com.nunop.doggobreed.breedDetails.data.local.entities.BreedDetailsDB
import com.nunop.doggobreed.breedDetails.data.remote.dto.BreedDetails

fun BreedDetails.toBreedDetailsDB(breed: String): BreedDetailsDB? {
    return BreedDetailsDB(
        breedOrSubBreed = breed,
        message = message ?: return null,
        status = status ?: return null
    )
}