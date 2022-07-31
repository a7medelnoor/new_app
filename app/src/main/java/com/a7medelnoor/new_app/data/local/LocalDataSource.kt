package com.a7medelnoor.new_app.data.local

import androidx.lifecycle.LiveData
import com.a7medelnoor.new_app.data.remote.api.ApiService
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val articlesDAO: ArticlesDAO
){

    // read articles to the database
     fun readArticlesDataBase(): LiveData<List<ArticlesEntity>> {
        return articlesDAO.readArticlesData()
    }
    // insert articles to the database
    suspend fun insertArticlesToTheDataBase(articlesEntity: ArticlesEntity){
        articlesDAO.insertArticlesData(articlesEntity)
    }
}