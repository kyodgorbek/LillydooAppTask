package yodgorbekkomilov.edgar.lillydooapptask.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context: Context): Interceptor {

    private val applicationContext = context.applicationContext

    private val mSessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoInternetException("Make sure you have an active data connection")

        val original = chain.request()

        Log.e("URL : ", " -=> ${original.url.encodedPath}, Method ${original.method}")

        if (original.url.encodedPath.contains("/api/user/me/") && original.method == "GET") {
            val requestBuilder = chain.request().newBuilder()
            // If token has been saved, add it to the request
            mSessionManager.fetchAuthToken()?.let {
                requestBuilder.addHeader("Authorization", "Bearer $it")
            }

            Log.e("Request :", " -=> $requestBuilder")

            val request = requestBuilder.build()
            return chain.proceed(request)
        }

        return  chain.proceed(original)
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }

}