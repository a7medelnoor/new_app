package com.a7medelnoor.new_app.repository

import com.a7medelnoor.new_app.data.local.LocalDataSource
import com.a7medelnoor.new_app.data.remote.RemoteDataSource
import com.a7medelnoor.new_app.data.remote.api.ApiService
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val apiService: ApiService,
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
){

//    suspend fun getArticles() = apiService.getNewsArticles()
    val remote = remoteDataSource
    val local = localDataSource

}