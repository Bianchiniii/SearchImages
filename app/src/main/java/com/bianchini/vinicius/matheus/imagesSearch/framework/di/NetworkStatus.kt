package com.bianchini.vinicius.matheus.imagesSearch.framework.di

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}
