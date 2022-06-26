package com.nunop.doggobreed.breeds.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nunop.doggobreed.R
import com.nunop.doggobreed.ui.theme.LocalSpacing

@Composable
fun BreedItem(
    breed: String,
    onToggleClick: () -> Unit,
    modifier: Modifier = Modifier,
    hasSubBreeds: Boolean = false,
    isExpanded: Boolean = false,
) {
    val spacing = LocalSpacing.current

    Card(
        shape = RoundedCornerShape(spacing.roundedCornerShape),
        modifier = Modifier
            .padding(spacing.spaceMedium)
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleClick() }
                    .padding(spacing.spaceMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pets),
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primaryVariant),
                    modifier = Modifier.padding(spacing.spaceSmall),
                    contentDescription = stringResource(id = R.string.pet)
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = breed,
                            style = MaterialTheme.typography.h3
                        )
                        if (hasSubBreeds) {
                            Icon(
                                imageVector = if (isExpanded) {
                                    Icons.Default.KeyboardArrowUp
                                } else Icons.Default.KeyboardArrowDown,
                                contentDescription = if (isExpanded) {
                                    stringResource(id = R.string.collapse)
                                } else stringResource(id = R.string.extend)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

        }
    }
}