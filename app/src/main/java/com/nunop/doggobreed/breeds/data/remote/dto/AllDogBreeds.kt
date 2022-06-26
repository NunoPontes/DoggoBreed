package com.nunop.doggobreed.breeds.data.remote.dto

data class AllDogBreeds(
    val message: Map<String, List<String>>? = null,
    val status: String? = null
)