package com.nunop.doggobreed.breeds.data.mapper

import com.nunop.doggobreed.breeds.data.remote.dto.AllDogBreeds
import com.nunop.doggobreed.breeds.presentation.AllBreedsState

fun AllDogBreeds.toAllBreedsState(isExpanded: Boolean): List<AllBreedsState> {
    val mutableList = mutableListOf<AllBreedsState>()

    message?.entries?.forEach {
        val breedState = AllBreedsState(
            breed = it.key,
            subBreeds = it.value,
            isExpanded = isExpanded
        )
        mutableList.add(breedState)
    }
    return mutableList
}