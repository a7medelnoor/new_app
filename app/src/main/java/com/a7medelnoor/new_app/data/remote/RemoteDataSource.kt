package com.a7medelnoor.new_app.data.remote

import com.a7medelnoor.new_app.data.remote.api.ApiService
import com.a7medelnoor.new_app.data.remote.model.NewsResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getArticles(apiKey: String): Response<NewsResponse> {
        return apiService.getNewsArticles(apiKey)
    }
}