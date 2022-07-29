package com.a7medelnoor.new_app.data.remote.api

import okhttp3.Interceptor
import okhttp3.Response

class MyIntercepter : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type","application.json")
            .addHeader("api-key","2oaHHG65c7UUwJmTGR0AyXCz4PCPuQ1N")
            .build()
        return chain.proceed(request)
    }
}