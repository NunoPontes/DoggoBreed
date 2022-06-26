package com.nunop.doggobreed.ui.components

import androidx.compose.runtime.Composable
import com.nunop.doggobreed.breedDetails.presentation.BreedDetailsState
import com.nunop.doggobreed.breeds.presentation.AllDogBreedsState

@Composable
fun ErrorStatus(state: AllDogBreedsState) {
    if (state.isLoading) {
        Loading()
    } else if (state.isError) {
        GenericError()
    } else if (state.isNoInternet) {
        NoInternet()
    }
    ConnectivityStatus()
}

@Composable
fun ErrorStatus(state: BreedDetailsState) {
    if (state.isLoading) {
        Loading()
    } else if (state.isError) {
        GenericError()
    } else if (state.isNoInternet) {
        NoInternet()
    }
    ConnectivityStatus()
}