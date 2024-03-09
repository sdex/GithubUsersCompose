package dev.sdex.github.data.source.remote

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class ForceCacheInterceptor(
    private val isConnected: () -> Boolean,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (!isConnected()) {
            builder.cacheControl(CacheControl.FORCE_CACHE)
        }
        return chain.proceed(builder.build());
    }
}
