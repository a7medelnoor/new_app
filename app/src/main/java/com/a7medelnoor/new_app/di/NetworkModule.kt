package com.a7medelnoor.new_app.di

import com.a7medelnoor.new_app.data.remote.api.ApiService
import com.a7medelnoor.new_app.util.Constants.BASE_URL
import com.a7medelnoor.new_app.data.remote.api.MyIntercepter
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyIntercepter())
    }.build()
    val builder = GsonBuilder().disableHtmlEscaping().create()

// provide retrofit
    @Provides
    fun provideRetrofit(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(builder))
            .build()
            .create(ApiService::class.java)

}
