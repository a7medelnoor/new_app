package com.a7medelnoor.new_app.data.local

import androidx.room.TypeConverter
import com.a7medelnoor.new_app.data.remote.model.NewsResponse
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class ArticlesTypeConverter {
    var gson = Gson()

    // convert articles object to string
    @TypeConverter
    fun articlesDataToString(articlesResponse: NewsResponse): String {
        return gson.toJson(articlesResponse)
    }

    // convert string to articles object
    @TypeConverter
    fun stringToArticlesData(data: String): NewsResponse {
        val listType = object : TypeToken<NewsResponse>() {}.type
        return gson.fromJson(data, listType)
    }

}