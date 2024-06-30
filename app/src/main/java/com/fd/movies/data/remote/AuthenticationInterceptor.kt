package com.fd.movies.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(private val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer $authToken")
            .build()
        return chain.proceed(request)
    }
}
