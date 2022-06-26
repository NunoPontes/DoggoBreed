package com.nunop.doggobreed.breeds.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExpandableBreed(
    breed: String,
    hasSubBreeds: Boolean,
    isExpanded: Boolean,
    onToggleClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier
) {
    BreedItem(
        breed = breed,
        hasSubBreeds = hasSubBreeds,
        isExpanded = isExpanded,
        onToggleClick = onToggleClick,
        modifier = modifier
    )
    AnimatedVisibility(visible = isExpanded) {
        content()
    }
}