package com.nunop.doggobreed.breedDetails.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nunop.doggobreed.breedDetails.domain.use_case.BreedDetailsUseCases
import com.nunop.doggobreed.core.utils.Error
import com.nunop.doggobreed.core.utils.Resource
import com.nunop.doggobreed.core.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailsViewModel @Inject constructor(private val breedDetailsUseCases: BreedDetailsUseCases) :
    ViewModel() {

    var state by mutableStateOf(BreedDetailsState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.InitFinished)
        }
    }

    fun getBreedDetails(breed: String, subBreed: String?) {

        viewModelScope.launch {

            state = state.copy(
                isLoading = true
            )
            breedDetailsUseCases.getBreedDetailsUseCases.invoke(
                breed = breed, subBreed
                = subBreed
            ).onStart {
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
                                message = result.data,
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
}