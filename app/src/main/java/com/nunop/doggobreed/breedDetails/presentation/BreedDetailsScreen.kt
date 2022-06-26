package com.nunop.doggobreed.breedDetails.presentation

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nunop.doggobreed.R
import com.nunop.doggobreed.core.utils.UiEvent
import com.nunop.doggobreed.ui.components.ErrorStatus
import com.nunop.doggobreed.ui.theme.LocalSpacing
import com.nunop.doggobreed.ui.utils.forwardingPainter
import com.nunop.doggobreed.ui.utils.header

@ExperimentalComposeUiApi
@Composable
fun BreedDetailsScreen(
    breed: String,
    subBreed: String?,
    viewModel: BreedDetailsViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state //TODO: should we use by remember?

    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.InitFinished -> {
                    viewModel.getBreedDetails(
                        breed = breed,
                        subBreed = subBreed
                    )
                }
            }
        }
    }


    LazyVerticalGrid(
        columns = GridCells.Adaptive(
            minSize = spacing.minImageSize
        ), content = {
            header {
                ErrorStatus(state)
            }
            items(state.message?.message ?: emptyList()) { item ->
                Card(
                    shape = RoundedCornerShape(spacing.roundedCornerShape),
                    modifier = Modifier
                        .padding(spacing.spaceMedium)
                        .fillMaxWidth()
                ) {
                    val forwardingPainter = forwardingPainter(
                        painterResource(id = R.drawable.ic_pets),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.primaryVariant)
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item)
                            .crossfade(true)
                            .build(),
                        placeholder = forwardingPainter,
                        fallback = forwardingPainter,
                        error = forwardingPainter,
                        contentDescription = stringResource(R.string.dog),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 5.dp,
                                    bottomStart = 5.dp
                                )
                            )
                    )
                }
            }
        }
    )
}