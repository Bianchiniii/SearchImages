package com.bianchini.vinicius.matheus.imagesSearch.framework.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NetworkStatusHelper(context: Context) : LiveData<NetworkStatus>() {
    val valideNetworkConnections: ArrayList<Network> = ArrayList()
    var connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    fun sendNetworkStatus(network: Network) {
        if (valideNetworkConnections.isNotEmpty()) {
            if (valideNetworkConnections.contains(network))
                postValue(NetworkStatus.Available)
            else postValue(NetworkStatus.Unavailable)
        } else {
            postValue(NetworkStatus.Unavailable)
        }
    }

    fun getConnectivityManagerCallback() =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                val networkCapability = connectivityManager.getNetworkCapabilities(network)
                val hasNetworkConnection = networkCapability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
                if (hasNetworkConnection) {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (InernetAvailablity.isOnline()) {
                            withContext(Dispatchers.Main) {
                                if (!valideNetworkConnections.contains(network))
                                    valideNetworkConnections.add(network)
                                sendNetworkStatus(network)
                            }
                        }
                    }
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                if (valideNetworkConnections.isNotEmpty()) {
                    valideNetworkConnections.forEach {
                        if (it == network)
                            valideNetworkConnections.remove(network)
                    }
                }
                sendNetworkStatus(network)
            }

            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (InernetAvailablity.isOnline()) {
                            withContext(Dispatchers.Main) {
                                if (!valideNetworkConnections.contains(network))
                                    valideNetworkConnections.add(network)
                            }
                        }
                    }
                } else {
                    if (valideNetworkConnections.isNotEmpty()) {
                        valideNetworkConnections.forEach {
                            if (it == network)
                                valideNetworkConnections.remove(network)
                        }
                    }
                }
                sendNetworkStatus(network)
            }
        }

    override fun onActive() {
        super.onActive()
        connectivityManagerCallback = getConnectivityManagerCallback()
        val networkRequest = NetworkRequest
            .Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }
}