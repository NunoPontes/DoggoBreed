package com.nunop.doggobreed.breedDetails.presentation

import com.nunop.doggobreed.breedDetails.data.remote.dto.BreedDetails

data class BreedDetailsState(
    val message: BreedDetails? = null,
    val status: String? = null,
    val isLoading: Boolean = false,
    val isNoInternet: Boolean = false,
    val isError: Boolean = false
)
