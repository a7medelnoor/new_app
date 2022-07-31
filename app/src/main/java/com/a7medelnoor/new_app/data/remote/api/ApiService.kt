package com.a7medelnoor.new_app.data.remote.api

import com.a7medelnoor.new_app.data.remote.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("all-sections/7.json?api-key=2oaHHG65c7UUwJmTGR0AyXCz4PCPuQ1N")
    suspend fun getNewsArticles(@Header("api-key") apikey: String): Response<NewsResponse>
}