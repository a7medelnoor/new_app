package com.a7medelnoor.new_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ArticlesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ArticlesTypeConverter::class)
abstract class ArticlesDataBase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDAO
}