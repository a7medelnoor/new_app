package com.a7medelnoor.new_app.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@ExperimentalCoroutinesApi
class NetworkListener : ConnectivityManager.NetworkCallback() {
    private val isNetworkAvailable = MutableStateFlow(false)

    fun checkNetworkAvailability(context: Context): MutableStateFlow<Boolean> {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(this)
        }
        var isConnected = false

        connectivityManager.allNetworks.forEach { network ->
            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            networkCapability?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    isConnected = true
                    return@forEach
                }
            }
        }
        isNetworkAvailable.value = isConnected
        return isNetworkAvailable
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        isNetworkAvailable.value = true
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        isNetworkAvailable.value = false
    }
}