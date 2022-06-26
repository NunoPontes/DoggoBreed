package com.nunop.doggobreed.breeds.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nunop.doggobreed.breeds.data.mapper.toAllBreedsState
import com.nunop.doggobreed.breeds.domain.use_case.BreedsUseCases
import com.nunop.doggobreed.core.utils.Error
import com.nunop.doggobreed.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AllDogBreedsViewModel @Inject constructor(private val breedsUseCases: BreedsUseCases) :
    ViewModel() {

    var state by mutableStateOf(AllDogBreedsState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            breedsUseCases.getDogBreeds.invoke()
                .onStart {
                    state = state.copy(
                        message = null,
                        isLoading = true,
                        isError = false,
                        isNoInternet = false
                    )
                }
                .catch {
                    state = state.copy(
                        message = null,
                        isLoading = false,
                        isError = true,
                        isNoInternet = false
                    )
                }
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                message = result.data?.toAllBreedsState(false),
                                status = result.data?.status,
                                isLoading = false,
                                isError = false,
                                isNoInternet = false
                            )
                        }
                        is Resource.Error -> {
                            if (result.message == Error.GENERIC.error) {
                                state = state.copy(
                                    message = null,
                                    isLoading = false,
                                    isError = true,
                                    isNoInternet = false
                                )
                            } else if (result.message == Error.NOINTERNET.error) {
                                state = state.copy(
                                    isLoading = false,
                                    isError = false,
                                    isNoInternet = true
                                )
                            }
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                message = null,
                                isLoading = true,
                                isError = false,
                                isNoInternet = false
                            )
                        }
                    }

                }
        }
    }

    fun onEvent(event: AllDogBreedsEvent) {
        when (event) {
            is AllDogBreedsEvent.OnClickExpandBreed -> {

                val breedList = state.message?.toMutableList()
                val breed = breedList?.find { it.breed == event.dogBreed }
                val changedBreed = breed?.copy(
                    isExpanded = !breed.isExpanded
                )
                (breedList as List<AllBreedsState?>?)?.let {
                    Collections.replaceAll(
                        it,
                        breed,
                        changedBreed
                    )
                }

                state = state.copy(
                    message = breedList
                )
            }
        }
    }
}