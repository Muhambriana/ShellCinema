package com.mshell.shellcinema.core.utils

import com.mshell.shellcinema.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter("api_key", NetworkInfo.API_KEY)
            .build()

        val requestWithHeaders = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .addHeader("User-Agent", "ShellCinema/${BuildConfig.VERSION_CODE}")
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(requestWithHeaders)
    }
}