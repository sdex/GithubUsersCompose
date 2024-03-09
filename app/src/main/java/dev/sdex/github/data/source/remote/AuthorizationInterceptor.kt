package dev.sdex.github.data.source.remote

import okhttp3.Interceptor
import okhttp3.Response

// TODO set your Github API token
private const val TOKEN = "SET YOUR GITHUB API TOKEN"

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $TOKEN")
            .build()
        return chain.proceed(request)
    }
}
