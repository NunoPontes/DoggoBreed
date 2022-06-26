package com.nunop.doggobreed.breeds.presentation

data class AllDogBreedsState(
    val message: List<AllBreedsState>? = null,
    val status: String? = null,
    val isLoading: Boolean = false,
    val isNoInternet: Boolean = false,
    val isError: Boolean = false
)