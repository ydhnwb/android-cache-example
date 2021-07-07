package com.ydhnwb.simplecaching.data.common.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.ydhnwb.simplecaching.data.common.exception.NoInternetConnectionException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor constructor(private val applicationContext: Context) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isConnected()){
            throw NoInternetConnectionException()
        }
        val newRequest = chain.request().newBuilder().build()
        return chain.proceed(newRequest)
    }

    private fun isConnected() : Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as  ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}