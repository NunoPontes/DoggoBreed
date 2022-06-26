package com.nunop.doggobreed.core.utils.network

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
