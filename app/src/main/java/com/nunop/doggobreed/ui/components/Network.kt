package com.nunop.doggobreed.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.nunop.doggobreed.R
import com.nunop.doggobreed.core.utils.network.ConnectionState
import com.nunop.doggobreed.core.utils.network.currentConnectivityState
import com.nunop.doggobreed.core.utils.network.observeConnectivityAsFlow
import com.nunop.doggobreed.ui.theme.green
import com.nunop.doggobreed.ui.theme.red
import kotlinx.coroutines.delay

@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    // Creates a State<ConnectionState> with current connectivity state as initial value
    return produceState(initialValue = context.currentConnectivityState) {
        // In a coroutine, can make suspend calls
        context.observeConnectivityAsFlow()
            .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect { value = it }
    }
}

@Composable
fun ConnectivityStatus() {
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    var visibility by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        ConnectivityStatusBox(isConnected = isConnected)
    }

    LaunchedEffect(isConnected) {
        visibility = if (!isConnected) {
            true
        } else {
            delay(2000)
            false
        }
    }
}

@Composable
fun ConnectivityStatusBox(isConnected: Boolean) {
    val backgroundColor by animateColorAsState(if (isConnected) green else red)
    val message =
        if (isConnected) stringResource(R.string.back_online) else stringResource(R.string.no_internet)
    val iconResource = if (isConnected) {
        R.drawable.ic_connectivity_available
    } else {
        R.drawable.ic_connectivity_unavailable
    }

    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painterResource(id = iconResource),
                stringResource(R.string.connectivity),
                tint = Color.White
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(message, color = Color.White, fontSize = 15.sp)
        }
    }
}