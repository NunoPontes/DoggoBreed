package com.nunop.doggobreed.breeds.presentation

data class AllBreedsState(
    val breed: String? = null,
    val subBreeds: List<String>? = null,
    val isExpanded: Boolean = false
)
