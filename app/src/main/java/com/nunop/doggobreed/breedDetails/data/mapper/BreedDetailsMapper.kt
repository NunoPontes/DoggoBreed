package com.nunop.doggobreed.breedDetails.data.mapper

import com.nunop.doggobreed.breedDetails.data.local.entities.BreedDetailsDB
import com.nunop.doggobreed.breedDetails.data.remote.dto.BreedDetails

fun BreedDetailsDB.toBreedDetails(): BreedDetails{
    return BreedDetails(
        message = message,
        status = status
    )
}