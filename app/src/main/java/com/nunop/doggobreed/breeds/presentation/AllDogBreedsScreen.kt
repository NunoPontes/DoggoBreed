package com.nunop.doggobreed.breeds.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import com.nunop.doggobreed.breeds.presentation.components.BreedItem
import com.nunop.doggobreed.breeds.presentation.components.ExpandableBreed
import com.nunop.doggobreed.ui.components.ErrorStatus
import com.nunop.doggobreed.ui.theme.LocalSpacing

@ExperimentalFoundationApi
@Composable
fun AllDogBreedsScreen(
    onNextClick: (String, String?) -> Unit,
    viewModel: AllDogBreedsViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state //TODO: should we use by remember?



    LazyColumn(modifier = Modifier.semantics { contentDescription = "ListBreeds" }) {
        stickyHeader {
            ErrorStatus(state)
        }

        items(state.message ?: emptyList()) { item ->
            val subBreeds = state.message?.find { it == item }?.subBreeds
            val expanded = state.message?.find { it == item }?.isExpanded
            ExpandableBreed(
                breed = item.breed ?: "",
                hasSubBreeds = (subBreeds?.size ?: 0) > 0,
                onToggleClick = {
                    if (subBreeds.isNullOrEmpty()) {
                        item.breed?.let { onNextClick(it, null) }

                    } else {
                        item.breed?.let { AllDogBreedsEvent.OnClickExpandBreed(it) }
                            ?.let { viewModel.onEvent(it) }
                    }
                },
                isExpanded = expanded ?: false,
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.spaceSmall)
                    ) {

                        subBreeds?.forEach { breed ->
                            BreedItem(
                                breed = breed,
                                onToggleClick = {
                                    item.breed?.let { onNextClick(it, breed) }
                                },
                                modifier = Modifier.semantics { contentDescription = "SubBreed" }
                            )
                            Spacer(modifier = Modifier.height(spacing.spaceMedium))
                        }

                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = "Breed" }
            )
        }
    }

}