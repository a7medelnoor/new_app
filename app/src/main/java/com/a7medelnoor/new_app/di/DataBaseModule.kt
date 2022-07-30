package com.a7medelnoor.new_app.di

import android.content.Context
import androidx.room.Room
import com.a7medelnoor.new_app.data.local.ArticlesDataBase
import com.a7medelnoor.new_app.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {


    // provide room database
    @Singleton
    @Provides
    fun provideRoomDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        ArticlesDataBase::class.java,
        DATABASE_NAME
    ).build()

    // provide articles dao
    @Singleton
    @Provides
    fun provideArticlesDao(articlesDataBase: ArticlesDataBase) = articlesDataBase.articlesDao()
}