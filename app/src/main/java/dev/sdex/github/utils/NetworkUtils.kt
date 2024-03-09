package dev.sdex.github.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun isConnected(connectivityManager: ConnectivityManager) =
    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

fun Context.isConnected(): Boolean =
    isConnected(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
