package com.a7medelnoor.new_app.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticlesDAO {
    // insert articles to the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticlesData(articlesEntity: ArticlesEntity)

    // read articles from the database
    @Query("SELECT * FROM articles_table ORDER BY id ASC")
    fun readArticlesData(): LiveData<List<ArticlesEntity>>
}