package com.a7medelnoor.new_app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.a7medelnoor.new_app.data.remote.model.NewsResponse
import com.a7medelnoor.new_app.util.Constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class ArticlesEntity(var myResponse: NewsResponse) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

}