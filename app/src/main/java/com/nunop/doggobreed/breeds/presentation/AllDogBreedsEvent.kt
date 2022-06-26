package com.nunop.doggobreed.breeds.presentation

sealed class AllDogBreedsEvent {
    data class OnClickExpandBreed(val dogBreed: String) : AllDogBreedsEvent()
}
