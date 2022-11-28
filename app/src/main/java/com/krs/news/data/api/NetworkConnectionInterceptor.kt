package com.krs.news.data.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.krs.news.data.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * interceptor for check internet connection
 */
class NetworkConnectionInterceptor constructor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isNetworkAvailable(context))
            throw NoInternetException("Make sure you have an active data connection")

        return chain.proceed(chain.request())
    }

    @Suppress("DEPRECATION")
    fun isNetworkAvailable(context: Context?): Boolean {
        var result = false
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

}